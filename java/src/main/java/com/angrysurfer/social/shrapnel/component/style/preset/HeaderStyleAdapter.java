package com.angrysurfer.social.shrapnel.component.style.preset;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.WebColors;

public class HeaderStyleAdapter extends CellStyleAdapter {

    public HeaderStyleAdapter() {
        super();
        setBackgroundColor(WebColors.getRGBColor("2c5994"));
        setFontColor(Color.WHITE);
        setBold();
    }
}
