package com.angrysurfer.social.shrapnel.component.style.preset;

import com.angrysurfer.social.shrapnel.util.FileUtil;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.WebColors;

import java.util.Properties;

public class HeaderStyleAdapter extends CellStyleAdapter {

    public HeaderStyleAdapter() {
        super();

        try {
            Properties properties = FileUtil.getProperties(FileUtil.DEFAULTS);
            setBackgroundColor(WebColors.getRGBColor(properties.getOrDefault("header.background", "2c5994").toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setFontColor(Color.WHITE);
        setBold();
    }
}
