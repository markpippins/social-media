package com.angrysurfer.social.shrapnel.export.component.writer.style.adapter;

import com.angrysurfer.social.shrapnel.Config;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFontFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static com.angrysurfer.social.shrapnel.Config.FONTS_FOLDER;

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

        Config config = Config.getInstance();
        setFontSize(config.containsKey(FONT_SIZE) ? Integer.parseInt(config.getProperty(FONT_SIZE).toString()) : DEFAULT_FONT_SIZE);
        setMargin(config.containsKey(MARGIN) ? Integer.parseInt(config.getProperty(MARGIN).toString()) : DEFAULT_MARGIN);
        setPadding(config.containsKey(PADDING) ? Integer.parseInt(config.getProperty(PADDING).toString()) : DEFAULT_PADDING);

        try {
            setFont(PdfFontFactory.createFont(
                    FontProgramFactory.createFont(Config.getInstance().getProperty(FONTS_FOLDER).toString() +
                            (config.containsKey(FONT_NAME) ?
                                    config.getProperty(FONT_NAME) : DEFAULT_FONT)), PdfEncodings.WINANSI, true));
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
