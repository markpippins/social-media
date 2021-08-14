package com.angrysurfer.social.shrapnel.export.component;

import com.angrysurfer.social.shrapnel.export.component.field.IField;

public interface IValueCalculator {
    Object calculateValue(IField field, Object item);
}
