package com.angrysurfer.social.shrapnel.example;

import com.angrysurfer.social.shrapnel.Config;
import com.angrysurfer.social.shrapnel.component.Export;
import com.angrysurfer.social.shrapnel.component.IValueCalculator;
import com.angrysurfer.social.shrapnel.component.IValueRenderer;
import com.angrysurfer.social.shrapnel.component.field.FieldSpec;
import com.angrysurfer.social.shrapnel.component.field.FieldTypeEnum;
import com.angrysurfer.social.shrapnel.component.field.IFieldSpec;
import com.angrysurfer.social.shrapnel.component.property.IPropertyAccessor;
import com.angrysurfer.social.shrapnel.component.writer.IRowWriter;
import com.angrysurfer.social.shrapnel.component.writer.filter.IDataFilter;
import com.angrysurfer.social.shrapnel.component.writer.style.FontSource;
import com.angrysurfer.social.shrapnel.component.writer.style.adapter.StyleAdapter;
import com.angrysurfer.social.shrapnel.component.writer.style.provider.ZebraStyleProvider;
import com.angrysurfer.social.shrapnel.services.factory.IExportFactory;
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

@Getter
@Setter
@Slf4j
public class FontConfigExport extends Export {
    static List<IFieldSpec> FIELDS = new ArrayList();

    static String NAME = "font-list";

    static IFieldSpec id = new FieldSpec("id", "id", FieldTypeEnum.STRING);
    static IFieldSpec sample = new FieldSpec("name", "Sample", FieldTypeEnum.STRING);
    static IFieldSpec path = new FieldSpec("path", "path", FieldTypeEnum.STRING);
    static IFieldSpec fontname = new FieldSpec("fontname", "fontname", FieldTypeEnum.STRING);

    static {
        id.setCalculated(true);
        fontname.setCalculated(true);
        FIELDS.add(id);
        FIELDS.add(fontname);
        FIELDS.add(sample);
        FIELDS.add(path);
    }

    public FontConfigExport() {
        super(NAME, FIELDS);
    }

    @Override
    public PageSize getPdfPageSize() {
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
        public StyleAdapter getCellStyle(Object item, IFieldSpec field, int row) {

            StyleAdapter style = super.getCellStyle(item, field, row);
            if (Objects.nonNull(field) && field.getPropertyName().equals("name"))
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

    static class FontListValueRenderer implements IValueRenderer {
        @Override
        public String render(IFieldSpec field, String value) {
            if (field.getPropertyName().equals("name"))
                try {
                    PdfFont font = FontSource.getPdfFont2(value.toString());
                    return font.getFontProgram().getFontNames().getFontName();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }

            else if (field.getPropertyName().equals("path")) {
                File f = new File(".");
                String path = f.getAbsolutePath().substring(0, f.getAbsolutePath().length() - 1);
                return value.replace(path, "");
            }

            return value;
        }

        @Override
        public boolean canRender(IFieldSpec field) {
            return Arrays.asList("path", "name").contains(field.getPropertyName());
        }

        @Override
        public String renderCalculatedValue(IFieldSpec field, Object value) {
            return Objects.nonNull(value) ? value.toString() : EMPTY_STRING;
        }
    }

    @Component
    public static class FontListExportFactory implements IExportFactory {

        @Override
        public Collection<Object> getData() {
            File file = new File(".");
            return Arrays.stream(new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 1)
                            + Config.getInstance().getProperty(Config.FONTS_FOLDER)).listFiles())
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
            return FontConfigExport.class;
        }
    }

    static class FontListValueCalculator implements IValueCalculator {
        private int count = 0;

        @Override
        public Object calculateValue(IFieldSpec field, Object item) {
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

    static class UniqueFontFilter implements IDataFilter {
        List<String> filenames = new ArrayList<>();

        @Override
        public boolean allows(Object item, IRowWriter writer, IPropertyAccessor accessor) {
            File f = (File) item;
            boolean result = !filenames.contains(f.getName().toLowerCase());
            filenames.add(f.getName().toLowerCase());
            log.info("allow({}) returning {}", f.getName(), result);
            return result;
        }
    }
}
