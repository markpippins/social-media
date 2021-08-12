package com.angrysurfer.social.shrapnel.component.field;

public interface IFieldSpec {

    String getPropertyName();

    String getLabel();

    Integer getIndex();

    void setIndex(Integer size);

    FieldTypeEnum getType();

    Boolean getCalculated();

    void setCalculated(Boolean isCalculated);
}
