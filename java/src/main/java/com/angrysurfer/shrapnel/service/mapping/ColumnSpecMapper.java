package com.angrysurfer.shrapnel.service.mapping;

import com.angrysurfer.shrapnel.component.property.ColumnSpec;
import com.angrysurfer.shrapnel.service.model.ColumnSpecification;

public class ColumnSpecMapper {

    public ColumnSpec create(ColumnSpecification spec) {
        ColumnSpec result = new ColumnSpec();
        result.setPropertyName(spec.getPropertyName());
        result.setHeaderLabel(spec.getHeaderLabel());
        result.setType(spec.getType());
        return result;
    }
}
