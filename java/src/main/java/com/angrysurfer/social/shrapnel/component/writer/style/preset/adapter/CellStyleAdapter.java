package com.angrysurfer.social.shrapnel.component.writer.style.preset.adapter;

import com.angrysurfer.social.shrapnel.component.writer.style.StyleAdapter;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFontFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

import static com.angrysurfer.social.shrapnel.component.writer.style.FontSource.FONTS_FOLDER;

@Setter
@Getter
@Slf4j
public class CellStyleAdapter extends StyleAdapter {

    private static final String FONT_NAME = "font.name";
    private static final String FONT_SIZE = "font.size";
    private static final String MARGIN = "cell.margin";
    private static final String PADDING = "cell.padding";

    static int DEFAULT_FONT_SIZE = 7;
    static int DEFAULT_MARGIN = 1;
    static int DEFAULT_PADDING = 1;

    static String DEFAULT_FONT = "comic.ttf";

    public CellStyleAdapter() {

        Properties defaults = getDefaults();

        setFontSize(defaults.containsKey(FONT_SIZE) ? Integer.parseInt(defaults.getProperty(FONT_SIZE)) : DEFAULT_FONT_SIZE);
        setMargin(defaults.containsKey(MARGIN) ? Integer.parseInt(defaults.getProperty(MARGIN)) : DEFAULT_MARGIN);
        setPadding(defaults.containsKey(PADDING) ? Integer.parseInt(defaults.getProperty(PADDING)) : DEFAULT_PADDING);

        try {
            setFont(PdfFontFactory.createFont(FontProgramFactory.createFont(FONTS_FOLDER +
                            (defaults.containsKey(FONT_NAME) ? defaults.getProperty(FONT_NAME) : DEFAULT_FONT)),
                    PdfEncodings.WINANSI, true));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            try {
                setFont(PdfFontFactory.createFont(FontConstants.COURIER));
            } catch (Exception e2) {
                log.error(e2.getMessage(), e2);
            }
        }
    }
}
