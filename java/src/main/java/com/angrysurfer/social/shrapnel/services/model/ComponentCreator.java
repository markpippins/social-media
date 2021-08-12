package com.angrysurfer.social.shrapnel.services.model;

import com.angrysurfer.social.shrapnel.component.field.FieldTypeEnum;

public class ComponentCreator {

    public static FieldType createFieldType(FieldTypeEnum fieldType) {
        FieldType ft = new FieldType();
        ft.setName(fieldType.name());
        ft.setCode(fieldType.getCode());
        return ft;
    }
}
