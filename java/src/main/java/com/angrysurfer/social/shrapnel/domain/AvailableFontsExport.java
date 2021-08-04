package com.angrysurfer.social.shrapnel.domain;

import com.angrysurfer.social.shrapnel.ExportFactory;
import com.angrysurfer.social.shrapnel.TabularExport;
import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.component.format.AbstractValueFormatter;
import com.angrysurfer.social.shrapnel.component.style.PDFFontSource;
import com.angrysurfer.social.shrapnel.component.style.StyleAdapter;
import com.angrysurfer.social.shrapnel.component.style.preset.ZebraStyleProvider;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static com.angrysurfer.social.shrapnel.component.style.PDFFontSource.FONTS_FOLDER;

@Getter
@Setter
public class AvailableFontsExport extends TabularExport {

    static List<ColumnSpec> COLUMNS = ColumnSpec.createColumnSpecs(Arrays.asList("path", "name"));

    static String NAME = "font-list";

    public AvailableFontsExport() {
        super(NAME, COLUMNS);
        getPdfRowWriter().setStyleProvider(new AvailableFontsStyleProvider());
        getPdfRowWriter().setValueFormatter(new AvailableFontsValueFormatter());
        getExcelRowWriter().setValueFormatter(new AvailableFontsValueFormatter());
    }

    static class AvailableFontsStyleProvider extends ZebraStyleProvider {

        public AvailableFontsStyleProvider() {
            super(Color.PINK);
            getDefaultCellStyleAdapter().setBackgroundColor(Color.ORANGE);
        }

        @Override
        public StyleAdapter getCellStyle(Object item, ColumnSpec col, int row) {

            StyleAdapter style = super.getCellStyle(item, col, row);

            if (Objects.nonNull(col) && col.getPropertyName().equals("name"))
                try {
                    StyleAdapter adapter = new StyleAdapter();
                    adapter.absorb(style);
                    String fontName = ((File) item).getName();
                    PdfFont font = PDFFontSource.getPdfFont2(fontName);
                    adapter.setFont(font);
                    return adapter;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            return style;
        }
    }

    static class AvailableFontsValueFormatter extends AbstractValueFormatter {

        @Override
        public String format(ColumnSpec col, String value) {
            if (col.getPropertyName().equals("name"))
                return Objects.nonNull(value) ? value.substring(0, value.length() - 4) : "";

            File f = new File(".");
            String path = f.getAbsolutePath().substring(0, f.getAbsolutePath().length() - 1);
            return value.replace(path, "");
        }

        @Override
        public boolean hasFormatFor(ColumnSpec col) {
            return true;
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
