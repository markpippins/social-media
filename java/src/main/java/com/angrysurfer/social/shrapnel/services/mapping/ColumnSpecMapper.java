package com.angrysurfer.social.shrapnel.services.mapping;

import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.services.model.ColumnSpecModel;

public class ColumnSpecMapper {

    public static FieldSpec create(ColumnSpecModel spec) {
        FieldSpec result = new FieldSpec();
        result.setPropertyName(spec.getPropertyName());
        result.setHeaderLabel(spec.getHeaderLabel());
        result.setType(spec.getType());
        result.setIndex(spec.getIndex());
        return result;
    }
}
