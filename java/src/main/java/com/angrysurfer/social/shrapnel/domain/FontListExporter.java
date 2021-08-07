package com.angrysurfer.social.shrapnel.domain;

import com.angrysurfer.social.shrapnel.TableExporter;
import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.component.format.AbstractValueFormatter;
import com.angrysurfer.social.shrapnel.component.style.PdfFontSource;
import com.angrysurfer.social.shrapnel.component.style.StyleAdapter;
import com.angrysurfer.social.shrapnel.component.style.preset.ZebraStyleProvider;
import com.angrysurfer.social.shrapnel.service.ExporterFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.angrysurfer.social.shrapnel.component.style.PdfFontSource.FONTS_FOLDER;

@Getter
@Setter
@Slf4j
public class FontListExporter extends TableExporter {

    static List<ColumnSpec> COLUMNS = ColumnSpec.createColumnSpecs(Arrays.asList("path", "name"));

    static String NAME = "font-list";

    public FontListExporter() {
        super(NAME, COLUMNS);
        setStyleProvider(new FontListStyleProvider(Color.PINK, IndexedColors.PALE_BLUE));
        setValueFormatter(new FontListValueFormatter());
    }

    static class FontListStyleProvider extends ZebraStyleProvider {
        public FontListStyleProvider(Color pdfDarkColor, IndexedColors excelDarkColor) {
            super(pdfDarkColor, excelDarkColor);
            getDefaultCellStyleAdapter().setBackgroundColor(Color.LIGHT_GRAY);
        }

//        @Override
//        public void onWorkbookSet(Workbook workbook) {
//            super.onWorkbookSet(workbook);
//            getCellStyle(workbook).setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//            getCellStyle(workbook).setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        }

        @Override
        public StyleAdapter getCellStyle(Object item, ColumnSpec col, int row) {

            StyleAdapter style = super.getCellStyle(item, col, row);

            if (Objects.nonNull(col) && col.getPropertyName().equals("name"))
                try {
                    StyleAdapter adapter = new StyleAdapter();
                    adapter.absorb(style);
                    String fontName = ((File) item).getName();
                    if (PdfFontSource.fontFileExists(fontName)) {
                        PdfFont font = PdfFontSource.getPdfFont2(fontName);
                        adapter.setFont(font);
                    }
                    return adapter;
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

            return style;
        }
    }

    static class FontListValueFormatter extends AbstractValueFormatter {

        @Override
        public String format(ColumnSpec col, String value) {
            if (col.getPropertyName().equals("name"))
                try {
                    PdfFont font = PdfFontSource.getPdfFont2(value.toString());
                    return font.getFontProgram().getFontNames().getFontName();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }

            // else if (col.getPropertyName().equals("path"))
            File f = new File(".");
            String path = f.getAbsolutePath().substring(0, f.getAbsolutePath().length() - 1);
            return value.replace(path, "");
        }

        @Override
        public boolean hasFormatFor(ColumnSpec col) {
            return Arrays.asList("path", "name").contains(col.getPropertyName());
        }
    }

    @Component
    public static class FontListExporterFactory implements ExporterFactory {

        @Override
        public Collection<Object> getData() {
            File file = new File(".");
            return Arrays.stream(new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 1) + FONTS_FOLDER)
                            .listFiles())
                    .filter(f -> f.getPath().toLowerCase(Locale.ROOT).endsWith(".ttf"))
                    .sorted(new Comparator<File>() {
                        @Override
                        public int compare(File file1, File file2) {
                            return file1.getAbsolutePath().toLowerCase(Locale.ROOT)
                                    .compareTo(file2.getAbsolutePath().toLowerCase(Locale.ROOT));
                        }
                    })
                    .collect(Collectors.toList());
        }

        @Override
        public String getExportName() {
            return NAME;
        }

        @Override
        public Class getExportClass() {
            return FontListExporter.class;
        }
    }
}
