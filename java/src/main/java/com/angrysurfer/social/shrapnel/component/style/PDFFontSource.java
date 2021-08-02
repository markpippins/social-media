package com.angrysurfer.social.shrapnel.component.style;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PDFFontSource {

    public final static String FONTS_FOLDER = "java/src/main/resources/fonts/";

    public static PdfFont getPdfFont(String name) throws IOException {
        PdfFont pdfFont = null;

        File file = new File(".");
        String fontProperties = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 1) + FONTS_FOLDER + "fonts.properties";

        try (InputStream inputStream = new FileInputStream(fontProperties)) {
            Properties properties = new Properties();
            properties.load(inputStream);

            String fontName = properties.getProperty("font.name");
            String fontFileName = getFontFileName(fontName);
            FontProgram fontProgram = FontProgramFactory.createFont(fontFileName);
            pdfFont = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI, true);
        }

        return pdfFont;
    }

    private static String getFontFileName(String fontName) {
        return FONTS_FOLDER + fontName + ".ttf";
    }
}
