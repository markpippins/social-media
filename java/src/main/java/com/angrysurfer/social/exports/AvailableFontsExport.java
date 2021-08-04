package com.angrysurfer.social.exports;

import com.angrysurfer.social.shrapnel.ExportFactory;
import com.angrysurfer.social.shrapnel.TabularExport;
import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.component.format.AbstractValueFormatter;
import com.angrysurfer.social.shrapnel.component.style.PDFFontSource;
import com.angrysurfer.social.shrapnel.component.style.PDFStyleProvider;
import com.angrysurfer.social.shrapnel.component.style.StyleAdapter;
import com.itextpdf.kernel.font.PdfFont;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static com.angrysurfer.social.shrapnel.component.style.PDFFontSource.FONTS_FOLDER;

public class AvailableFontsExport extends TabularExport {

    static List<ColumnSpec> COLUMNS = ColumnSpec.createColumnSpecs(Arrays.asList("name", "absolutePath"));

    static String NAME = "font-list";

    public AvailableFontsExport() {
        super(NAME, COLUMNS);
        getPdfRowWriter().setStyleProvider(new AvailableFontsStyleProvider());

        getPdfRowWriter().setValueFormatter(new AvailableFontsFormatter());
        getExcelRowWriter().setValueFormatter(new AvailableFontsFormatter());
    }

    static class AvailableFontsStyleProvider extends PDFStyleProvider {

        @Override
        public StyleAdapter getCellStyle(Object item, ColumnSpec col, int row) {
            if (Objects.isNull(item) || !col.getPropertyName().equals("name"))
                return super.getCellStyle(item, col, row);
            
            StyleAdapter style = new StyleAdapter();

            try {
                String fontName = ((File) item).getName();
                PdfFont font = PDFFontSource.getPdfFont2(fontName);
                style.setFont(font);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return style;
        }
    }

    static class AvailableFontsFormatter extends AbstractValueFormatter {

        @Override
        public String format(ColumnSpec col, String value) {
            return Objects.nonNull(value) ? value.substring(0, value.length() - 4) : "";
        }

        @Override
        public boolean hasFormatFor(ColumnSpec col) {
            return col.getPropertyName().equals("name");
        }
    }

    @Component
    public static class AvailableFontsExportFactory implements ExportFactory {

        @Override
        public Collection<Object> getData() {
            File file = new File(".");
            String fonts = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 1) + FONTS_FOLDER;
            Collection files = Arrays.stream(new File(fonts).listFiles())
                    .filter(f -> f.getPath().toLowerCase(Locale.ROOT).endsWith(".ttf"))
                    .sorted(new Comparator<File>() {
                        @Override
                        public int compare(File file1, File file2) {
                            return file1.getAbsolutePath().toLowerCase(Locale.ROOT)
                                    .compareTo(file2.getAbsolutePath().toLowerCase(Locale.ROOT));
                        }
                    })
                    .collect(Collectors.toList());

            return files;
        }

        @Override
        public String getExportName() {
            return NAME;
        }

        @Override
        public Class getExportClass() {
            return AvailableFontsExport.class;
        }
    }
}
