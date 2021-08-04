package com.angrysurfer.social.shrapnel.component.style;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PDFFontSource {

    public final static String FONTS_FOLDER = "java/src/main/resources/fonts/";

    final static Map<String, PdfFont> fonts = new HashMap<>();

    public static PdfFont getPdfFont(String fontName) throws IOException {

        if (fonts.containsKey(fontName))
            return fonts.get(fontName);

        String fontFileName = FONTS_FOLDER + fontName + ".ttf";
        FontProgram fontProgram = FontProgramFactory.createFont(fontFileName);
        fonts.put(fontName, PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI, true));

        return fonts.get(fontName);
    }

    public static PdfFont getPdfFont2(String fontName) throws IOException {

        if (fonts.containsKey(fontName))
            return fonts.get(fontName);

        String fontFileName = FONTS_FOLDER + fontName;
        FontProgram fontProgram = FontProgramFactory.createFont(fontFileName);
        fonts.put(fontName, PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI, true));

        return fonts.get(fontName);
    }

}
