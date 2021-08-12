package com.angrysurfer.social.shrapnel.component.writer.style.provider;

import com.angrysurfer.social.shrapnel.component.field.IFieldSpec;
import com.angrysurfer.social.shrapnel.component.writer.style.adapter.StyleAdapter;

public interface IStyleProvider {

    StyleAdapter getCellStyle(Object item, IFieldSpec field, int row);

    default StyleAdapter getCellStyle() {
        return getCellStyle(null, null, 0);
    }

    default StyleAdapter getCellStyle(IFieldSpec field) {
        return getCellStyle(null, field, 0);
    }

    StyleAdapter getHeaderStyle(IFieldSpec field);

    default StyleAdapter getHeaderStyle() {
        return getHeaderStyle(null);
    }
}
