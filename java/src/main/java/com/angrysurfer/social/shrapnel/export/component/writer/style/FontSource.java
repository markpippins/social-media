package com.angrysurfer.social.shrapnel.export.component.writer.style;

import com.angrysurfer.social.shrapnel.Config;
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
public class FontSource {

    static boolean deleteOnError = Config.getInstance().containsKey(Config.DELETE_ON_ERROR) ?
            Boolean.parseBoolean(Config.getInstance().getProperty(Config.DELETE_ON_ERROR).toString()) : false;

    public static boolean fontFileExists(String fontName) throws IOException {

        File file = new File(Config.getInstance().getProperty(Config.FONTS_FOLDER).toString());
        if ((Stream.of(file.listFiles()).filter(f -> f.getName().endsWith(".ttf")).collect(Collectors.toList()).size() > 0) ||
                (Stream.of(file.listFiles()).filter(f -> f.getName().endsWith(".TTF")).collect(Collectors.toList()).size() > 0))
            return true;

        return false;
    }

    public static PdfFont getPdfFont(String fontName) throws IOException {

        File file = new File(Config.getInstance().getProperty(Config.FONTS_FOLDER).toString());
        if (Stream.of(file.listFiles()).filter(f -> f.getName().endsWith(".ttf")).collect(Collectors.toList()).size() > 0)
            return getPdfFont2(fontName + ".ttf");

        else if (Stream.of(file.listFiles()).filter(f -> f.getName().endsWith(".TTF")).collect(Collectors.toList()).size() > 0)
            return getPdfFont2(fontName + ".TTF");

        return null;
    }

    public static PdfFont getPdfFont2(String fontName) throws IOException {
        String fontFileName = Config.getInstance().getProperty(Config.FONTS_FOLDER) + fontName;
        FontProgram fontProgram = FontProgramFactory.createFont(fontFileName);
        try {
            return PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI, true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);

            if (deleteOnError) {
                log.info("marking {} for deletion.", fontFileName);
                File f = new File(fontFileName);
                if (f.exists() && f.isFile())
                    f.delete();
            }

            throw e;
        }
    }
}
