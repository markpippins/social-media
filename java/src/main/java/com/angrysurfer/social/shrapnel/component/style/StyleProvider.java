package com.angrysurfer.social.shrapnel.component.style;

import com.angrysurfer.social.shrapnel.component.ColumnSpec;

public interface StyleProvider {

    StyleAdapter getCellStyle(Object item, ColumnSpec col, int row);

    default StyleAdapter getCellStyle() {
        return getCellStyle(null, null, 0);
    }

    default StyleAdapter getCellStyle(ColumnSpec col) {
        return getCellStyle(null, col, 0);
    }

    StyleAdapter getHeaderStyle(ColumnSpec col);

    default StyleAdapter getHeaderStyle() {
        return getHeaderStyle(null);
    }
}