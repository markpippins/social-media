package com.angrysurfer.social.shrapnel.export.service.model;

import com.angrysurfer.social.shrapnel.export.component.field.FieldTypeEnum;

public class ComponentCreator {

    public static FieldType createFieldType(FieldTypeEnum fieldType) {
        FieldType ft = new FieldType();
        ft.setName(fieldType.name());
        ft.setCode(fieldType.getCode());
        return ft;
    }
}
