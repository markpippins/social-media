package com.angrysurfer.social.shrapnel.component.writer.filter;

import com.angrysurfer.social.shrapnel.component.property.IPropertyAccessor;
import com.angrysurfer.social.shrapnel.component.writer.IDataWriter;

public interface IDataFilter {
    boolean allows(Object item, IDataWriter writer, IPropertyAccessor accessor);
}
