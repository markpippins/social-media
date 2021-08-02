package com.angrysurfer.social.shrapnel.component.filter;

import com.angrysurfer.social.shrapnel.component.property.PropertyAccessor;
import com.angrysurfer.social.shrapnel.component.writer.RowWriter;


public interface DataFilter {


    boolean allows(Object item, RowWriter writer, PropertyAccessor accessor);
}
