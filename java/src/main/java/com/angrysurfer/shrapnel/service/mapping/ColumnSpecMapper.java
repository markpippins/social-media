package com.angrysurfer.shrapnel.service.mapping;

import com.angrysurfer.shrapnel.component.property.ColumnSpec;

public class ColumnSpecMapper {

    public ColumnSpec create(com.angrysurfer.shrapnel.service.model.ColumnSpec spec) {
        return new ColumnSpec(spec.getPropertyName(), spec.getHeaderLabel(), spec.getType());
    }
}
