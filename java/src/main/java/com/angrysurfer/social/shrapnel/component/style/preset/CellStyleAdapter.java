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

    static int fontSize = 7;
    static int padding = 1;
    static int margin = 1;

    static String defaultFont = "comic.ttf";

    public CellStyleAdapter() {
        PdfFont pdfFont = null;
        String fontName = defaultFont;

        try (InputStream input = new FileInputStream(DEFAULTS)) {
            Properties properties = new Properties();
            properties.load(input);

            setPadding(properties.containsKey("cell.padding") ?
                    Integer.parseInt(properties.getProperty("cell.padding")) : padding);

            setMargin(properties.containsKey("cell.margin") ?
                    Integer.parseInt(properties.getProperty("cell.margin")) : margin);

            setFontSize(properties.containsKey("font.size") ?
                    Integer.parseInt(properties.getProperty("font.size")) : fontSize);

            if (properties.containsKey("font.name"))
                fontName = properties.getProperty("font.name");

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
