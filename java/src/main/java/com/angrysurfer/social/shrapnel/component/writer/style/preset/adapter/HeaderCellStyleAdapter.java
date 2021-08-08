package com.angrysurfer.social.shrapnel.component.writer.style.preset.adapter;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.WebColors;

import java.util.Properties;

public class HeaderCellStyleAdapter extends CellStyleAdapter {

    static String GREEN = "1c8214";

    public HeaderCellStyleAdapter() {
        super();
        Properties defaults = getDefaults();
        setBackgroundColor(WebColors.getRGBColor(defaults.getOrDefault("header.background", GREEN).toString()));
        setFontColor(defaults.containsKey("header.font.color") ?
                WebColors.getRGBColor(defaults.get("header.font.color").toString()) :
                Color.WHITE);
        setBold();
    }
}
