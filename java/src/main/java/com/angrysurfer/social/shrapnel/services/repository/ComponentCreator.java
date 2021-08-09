package com.angrysurfer.social.shrapnel.services.repository;

import com.angrysurfer.social.shrapnel.services.model.FieldSpec;
import com.angrysurfer.social.shrapnel.services.model.FieldType;
import com.angrysurfer.social.shrapnel.services.model.PdfPageSize;

public class ComponentCreator {

    public static com.angrysurfer.social.shrapnel.component.FieldSpec createFieldSpec(FieldSpec fieldSpec) {
        com.angrysurfer.social.shrapnel.component.FieldSpec result = new com.angrysurfer.social.shrapnel.component.FieldSpec();
        result.setPropertyName(fieldSpec.getPropertyName());
        result.setLabel(fieldSpec.getLabel());
        result.setType(com.angrysurfer.social.shrapnel.component.FieldSpec.FieldTypeEnum.from(fieldSpec.getFieldType().getName()));
        result.setIndex(fieldSpec.getIndex());
        return result;
    }

    public static FieldType createFieldType(com.angrysurfer.social.shrapnel.component.FieldSpec.FieldTypeEnum fieldType) {
        FieldType ft = new FieldType();
        ft.setName(fieldType.name());
        ft.setCode(fieldType.getCode());
        return ft;
    }

    public static com.itextpdf.kernel.geom.PageSize getPageSize(PdfPageSize pdfPageSize) {
        return new com.itextpdf.kernel.geom.PageSize(pdfPageSize.getWidth(), pdfPageSize.getHeight());
    }
}
