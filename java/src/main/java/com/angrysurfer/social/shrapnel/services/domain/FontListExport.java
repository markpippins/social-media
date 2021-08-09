package com.angrysurfer.social.shrapnel.services.domain;

import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.FieldTypeEnum;
import com.angrysurfer.social.shrapnel.component.property.PropertyAccessor;
import com.angrysurfer.social.shrapnel.component.writer.DataWriter;
import com.angrysurfer.social.shrapnel.component.writer.ValueCalculator;
import com.angrysurfer.social.shrapnel.component.writer.ValueRenderer;
import com.angrysurfer.social.shrapnel.component.writer.filter.DataFilter;
import com.angrysurfer.social.shrapnel.component.writer.style.FontSource;
import com.angrysurfer.social.shrapnel.component.writer.style.StyleAdapter;
import com.angrysurfer.social.shrapnel.component.writer.style.preset.provider.ZebraStyleProvider;
import com.angrysurfer.social.shrapnel.services.TabularExport;
import com.angrysurfer.social.shrapnel.services.factory.ExportFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.angrysurfer.social.shrapnel.component.writer.RowWriter.EMPTY_STRING;
import static com.angrysurfer.social.shrapnel.component.writer.style.FontSource.FONTS_FOLDER;

@Getter
@Setter
@Slf4j
public class FontListExport extends TabularExport {
    static List<FieldSpec> FIELDS = new ArrayList();

    static String NAME = "font-list";

    static FieldSpec id = new FieldSpec("id", "id", FieldTypeEnum.STRING);
    static FieldSpec sample = new FieldSpec("name", "Sample", FieldTypeEnum.STRING);
    static FieldSpec path = new FieldSpec("path", "path", FieldTypeEnum.STRING);
    static FieldSpec fontname = new FieldSpec("fontname", "fontname", FieldTypeEnum.STRING);

    static {
        id.setCalculated(true);
        fontname.setCalculated(true);
        FIELDS.add(id);
        FIELDS.add(fontname);
        FIELDS.add(sample);
        FIELDS.add(path);
    }

    public FontListExport() {
        super(NAME, FIELDS);
    }

    @Override
    public PageSize getPageSize() {
        return PageSize.A2;
    }

    @Override
    public void init() {
        setStyleProvider(new FontListStyleProvider(Color.LIGHT_GRAY, IndexedColors.PALE_BLUE));
        setValueRenderer(new FontListValueRenderer());
        setValueCalculator(new FontListValueCalculator());
        addFilter(new UniqueFontFilter());
    }

    static class FontListStyleProvider extends ZebraStyleProvider {
        public FontListStyleProvider(Color pdfDarkColor, IndexedColors excelDarkColor) {
            super(pdfDarkColor, excelDarkColor);
            getDefaultCellStyleAdapter().setBackgroundColor(Color.WHITE);
        }

//        @Override
//        public void onWorkbookSet(Workbook workbook) {
//            super.onWorkbookSet(workbook);
//            getCellStyle(workbook).setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//            getCellStyle(workbook).setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        }

        @Override
        public StyleAdapter getCellStyle(Object item, FieldSpec col, int row) {

            StyleAdapter style = super.getCellStyle(item, col, row);
            if (Objects.nonNull(col) && col.getPropertyName().equals("name"))
                try {
                    StyleAdapter adapter = new StyleAdapter();
                    adapter.absorb(style);
                    String fontName = ((File) item).getName();
                    if (FontSource.fontFileExists(fontName)) {
                        PdfFont font = FontSource.getPdfFont2(fontName);
                        adapter.setFont(font);
                    }
                    return adapter;
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

            return style;
        }
    }

    static class FontListValueRenderer implements ValueRenderer {


        @Override
        public String render(FieldSpec col, String value) {
            if (col.getPropertyName().equals("name"))
                try {
                    PdfFont font = FontSource.getPdfFont2(value.toString());
                    return font.getFontProgram().getFontNames().getFontName();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }

            else if (col.getPropertyName().equals("path")) {
                File f = new File(".");
                String path = f.getAbsolutePath().substring(0, f.getAbsolutePath().length() - 1);
                return value.replace(path, "");
            }

            return value;
        }

        @Override
        public boolean canRender(FieldSpec col) {
            return Arrays.asList("path", "name").contains(col.getPropertyName());
        }

        @Override
        public String renderCalculatedValue(FieldSpec field, Object value) {
            return Objects.nonNull(value) ? value.toString() : EMPTY_STRING;
        }
    }

    @Component
    public static class FontListExportFactory implements ExportFactory {

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
            return FontListExport.class;
        }
    }

    static class FontListValueCalculator implements ValueCalculator {
        private int count = 0;

        @Override
        public Object calculateValue(FieldSpec field, Object item) {
            if (field.getPropertyName().equalsIgnoreCase("fontname")) {
                String fontName = ((File) item).getName();
                try {
                    if (FontSource.fontFileExists(fontName)) {
                        PdfFont font = FontSource.getPdfFont2(fontName);
                        return font.getFontProgram().getFontNames().getFontName();
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            } else if (Objects.nonNull(field) && field.getPropertyName().equals("id")) {
                return Integer.toString(count++);
            }

            return EMPTY_STRING;
        }
    }

    static class UniqueFontFilter implements DataFilter {
        List<String> filenames = new ArrayList<>();

        @Override
        public boolean allows(Object item, DataWriter writer, PropertyAccessor accessor) {
            File f = (File) item;
            boolean result = !filenames.contains(f.getName().toLowerCase());
            filenames.add(f.getName().toLowerCase());
            log.info("allow({}) returning {}", f.getName(), result);
            return result;
        }
    }
}
