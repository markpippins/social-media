package com.angrysurfer.shrapnel.component.filter;

import com.angrysurfer.shrapnel.component.property.PropertyAccessor;
import com.angrysurfer.shrapnel.component.writer.RowWriter;


public interface DataFilter {


    boolean allows(Object item, RowWriter writer, PropertyAccessor accessor);
}
