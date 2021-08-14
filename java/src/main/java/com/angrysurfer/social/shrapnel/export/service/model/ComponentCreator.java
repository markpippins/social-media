package com.angrysurfer.social.shrapnel.export.service.model;

import com.angrysurfer.social.shrapnel.export.component.field.FieldTypeEnum;
import com.angrysurfer.social.shrapnel.export.service.model.export.FieldType;

public class ComponentCreator {

    public static FieldType createFieldType(FieldTypeEnum fieldType) {
        FieldType ft = new FieldType();
        ft.setName(fieldType.name());
        ft.setCode(fieldType.getCode());
        return ft;
    }
}
