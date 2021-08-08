package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.FieldSpec;

public interface ValueCalculator {
    Object calculateValue(FieldSpec field, Object item);
}
