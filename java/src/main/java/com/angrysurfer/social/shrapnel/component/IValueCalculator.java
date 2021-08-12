package com.angrysurfer.social.shrapnel.component;

import com.angrysurfer.social.shrapnel.component.field.IFieldSpec;

public interface IValueCalculator {
    Object calculateValue(IFieldSpec field, Object item);
}
