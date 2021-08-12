package com.angrysurfer.social.shrapnel.services.model;

import com.angrysurfer.social.shrapnel.component.field.FieldSpec;
import com.angrysurfer.social.shrapnel.component.field.FieldTypeEnum;
import com.angrysurfer.social.shrapnel.component.field.IFieldSpec;

public class ComponentCreator {

    public static FieldSpec createFieldSpec(IFieldSpec fieldSpec) {
        FieldSpec result = new FieldSpec();
        result.setPropertyName(fieldSpec.getPropertyName());
        result.setLabel(fieldSpec.getLabel());
        result.setType(FieldTypeEnum.from(fieldSpec.getFieldTypeName()));
        result.setIndex(fieldSpec.getIndex());
        return result;
    }

    public static FieldType createFieldType(FieldTypeEnum fieldType) {
        FieldType ft = new FieldType();
        ft.setName(fieldType.name());
        ft.setCode(fieldType.getCode());
        return ft;
    }

    public static com.itextpdf.kernel.geom.PageSize createPageSize(PdfPageSize pdfPageSize) {
        return new com.itextpdf.kernel.geom.PageSize(pdfPageSize.getWidth(), pdfPageSize.getHeight());
    }
}
