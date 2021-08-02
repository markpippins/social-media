package com.angrysurfer.social.shrapnel.service.mapping;

import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.service.model.ColumnSpecModel;

public class ColumnSpecMapper {

    public static ColumnSpec create(ColumnSpecModel spec) {
        ColumnSpec result = new ColumnSpec();
        result.setPropertyName(spec.getPropertyName());
        result.setHeaderLabel(spec.getHeaderLabel());
        result.setType(spec.getType());
        return result;
    }
}