package com.angrysurfer.shrapnel.component.style.preset;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.WebColors;
import com.itextpdf.layout.property.Property;

public class HeaderStyleAdapter extends CellStyleAdapter {

    public HeaderStyleAdapter() {
        super();
        if (hasProperty(Property.FONT)) {
            setBackgroundColor(WebColors.getRGBColor("2c5994"));
            setFontColor(Color.WHITE);
            setBold();
        }
    }
}
