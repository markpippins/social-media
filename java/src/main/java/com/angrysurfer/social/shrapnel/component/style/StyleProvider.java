package com.angrysurfer.social.shrapnel.component.style;

import com.angrysurfer.social.shrapnel.component.FieldSpec;

public interface StyleProvider {

    StyleAdapter getCellStyle(Object item, FieldSpec col, int row);

    default StyleAdapter getCellStyle() {
        return getCellStyle(null, null, 0);
    }

    default StyleAdapter getCellStyle(FieldSpec col) {
        return getCellStyle(null, col, 0);
    }

    StyleAdapter getHeaderStyle(FieldSpec col);

    default StyleAdapter getHeaderStyle() {
        return getHeaderStyle(null);
    }
}
