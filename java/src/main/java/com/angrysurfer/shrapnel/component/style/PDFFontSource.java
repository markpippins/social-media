package com.angrysurfer.shrapnel.component.style;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PDFFontSource {

    public final static String FONTS_FOLDER = "src/main/resources/fonts/";

    public static PdfFont getPdfFont(String name) throws IOException {
        PdfFont pdfFont = null;

        try (InputStream inputStream = new FileInputStream(FONTS_FOLDER + "fonts.properties")) {
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
