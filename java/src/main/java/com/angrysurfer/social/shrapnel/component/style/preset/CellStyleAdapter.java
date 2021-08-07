package com.angrysurfer.social.shrapnel.component.style.preset;

import com.angrysurfer.social.shrapnel.component.style.StyleAdapter;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

import static com.angrysurfer.social.shrapnel.component.style.PdfFontSource.FONTS_FOLDER;

@Setter
@Getter
@Slf4j
public class CellStyleAdapter extends StyleAdapter {

    static final String DEFAULTS = "java/src/main/resources/pdf.properties";

    private static final String FONT_SIZE = "font.size";
    private static final String MARGIN = "cell.margin";
    private static final String PADDING = "cell.padding";

    static int DEFAULT_FONT_SIZE = 7;
    static int DEFAULT_MARGIN = 1;
    static int DEFAULT_PADDING = 1;

    static String defaultFont = "comic.ttf";

    public CellStyleAdapter() {
        PdfFont pdfFont = null;

        try (InputStream input = new FileInputStream(DEFAULTS)) {
            Properties properties = new Properties();
            properties.load(input);

            setFontSize(properties.containsKey(FONT_SIZE) ? Integer.parseInt(properties.getProperty(FONT_SIZE)) : DEFAULT_FONT_SIZE);
            setMargin(properties.containsKey(MARGIN) ? Integer.parseInt(properties.getProperty(MARGIN)) : DEFAULT_MARGIN);
            setPadding(properties.containsKey(PADDING) ? Integer.parseInt(properties.getProperty(PADDING)) : DEFAULT_PADDING);

            String fontName = properties.containsKey("font.name") ? properties.getProperty("font.name") : defaultFont;
            String fontFileName = FONTS_FOLDER + fontName;
            FontProgram fontProgram = FontProgramFactory.createFont(fontFileName);
            pdfFont = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI, true);
        } catch (Exception e) {
            try {
                pdfFont = PdfFontFactory.createFont(FontConstants.COURIER);
            } catch (Exception e2) {
                log.error(e2.getMessage(), e2);
            }
        }

        if (Objects.nonNull(pdfFont))
            setFont(pdfFont);
    }
}
