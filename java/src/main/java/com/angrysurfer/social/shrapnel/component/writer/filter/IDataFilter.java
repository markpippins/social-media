package com.angrysurfer.social.shrapnel.component.writer.filter;

import com.angrysurfer.social.shrapnel.component.property.IPropertyAccessor;
import com.angrysurfer.social.shrapnel.component.writer.IRowWriter;


public interface IDataFilter {
    boolean allows(Object item, IRowWriter writer, IPropertyAccessor accessor);
}
