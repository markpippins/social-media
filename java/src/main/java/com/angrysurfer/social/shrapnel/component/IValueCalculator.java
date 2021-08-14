package com.angrysurfer.social.shrapnel.component;

import com.angrysurfer.social.shrapnel.component.field.IField;

public interface IValueCalculator {
    Object calculateValue(IField field, Object item);
}
