package com.angrysurfer.social.shrapnel.component.field;

import java.util.List;

public interface IFieldSpecs extends List<IFieldSpec> {

    default void addFieldSpec(IFieldSpec fieldSpec) {
        fieldSpec.setIndex(size());
        add(fieldSpec);
    }
}
