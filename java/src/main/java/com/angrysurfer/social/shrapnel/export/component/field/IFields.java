package com.angrysurfer.social.shrapnel.export.component.field;

import java.util.List;

public interface IFields extends List<IField> {

    default void addFieldSpec(IField fieldSpec) {
        fieldSpec.setIndex(size());
        add(fieldSpec);
    }
}
