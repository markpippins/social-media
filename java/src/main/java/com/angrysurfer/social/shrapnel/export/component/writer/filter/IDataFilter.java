package com.angrysurfer.social.shrapnel.export.component.writer.filter;

import com.angrysurfer.social.shrapnel.export.component.property.IPropertyAccessor;
import com.angrysurfer.social.shrapnel.export.component.writer.IDataWriter;

public interface IDataFilter {
    boolean allows(Object item, IDataWriter writer, IPropertyAccessor accessor);
}
