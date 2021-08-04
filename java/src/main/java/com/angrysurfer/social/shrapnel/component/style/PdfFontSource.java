package com.angrysurfer.social.shrapnel.component.style;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class PdfFontSource {

    public final static String FONTS_FOLDER = "java/src/main/resources/fonts/";

    public static boolean fontFileExists(String fontName) throws IOException {

        File file = new File(FONTS_FOLDER);
        if ((Stream.of(file.listFiles()).filter(f -> f.getName().endsWith(".ttf")).collect(Collectors.toList()).size() > 0) ||
                (Stream.of(file.listFiles()).filter(f -> f.getName().endsWith(".TTF")).collect(Collectors.toList()).size() > 0))
            return true;

        return false;
    }

    public static PdfFont getPdfFont(String fontName) throws IOException {

        File file = new File(FONTS_FOLDER);
        if (Stream.of(file.listFiles()).filter(f -> f.getName().endsWith(".ttf")).collect(Collectors.toList()).size() > 0)
            return getPdfFont2(fontName + ".ttf");

        else if (Stream.of(file.listFiles()).filter(f -> f.getName().endsWith(".TTF")).collect(Collectors.toList()).size() > 0)
            return getPdfFont2(fontName + ".TTF");

        return null;
    }

    public static PdfFont getPdfFont2(String fontName) throws IOException {
        String fontFileName = FONTS_FOLDER + fontName;
        FontProgram fontProgram = FontProgramFactory.createFont(fontFileName);
        return PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI, true);
    }

}
