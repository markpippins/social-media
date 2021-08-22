package com.angrysurfer.shrapnel.export.component.writer.style.adapter;

import com.angrysurfer.shrapnel.PropertyConfig;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.WebColors;

public class HeaderCellStyleAdapter extends CellStyleAdapter {

    static String GREEN = "1c8214";

    public HeaderCellStyleAdapter() {
        super();
        PropertyConfig defaults = PropertyConfig.getInstance();
        setBackgroundColor(WebColors.getRGBColor(defaults.getOrDefault("header.background", GREEN).toString()));
        setFontColor(defaults.containsKey("header.font.color") ?
                WebColors.getRGBColor(defaults.getProperty("header.font.color").toString()) :
                Color.WHITE);
        setBold();
    }
}
