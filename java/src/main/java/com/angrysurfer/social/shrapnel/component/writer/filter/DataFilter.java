package com.angrysurfer.social.shrapnel.component.writer.filter;

import com.angrysurfer.social.shrapnel.component.property.PropertyAccessor;
import com.angrysurfer.social.shrapnel.component.writer.DataWriter;


public interface DataFilter {
    boolean allows(Object item, DataWriter writer, PropertyAccessor accessor);
}
