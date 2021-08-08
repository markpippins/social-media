package com.angrysurfer.social.shrapnel.component.writer.filter;

import com.angrysurfer.social.shrapnel.component.property.PropertyAccessor;
import com.angrysurfer.social.shrapnel.component.writer.DataWriter;
import com.angrysurfer.social.shrapnel.component.writer.RowWriter;

import java.util.ArrayList;
import java.util.List;

public interface DataFilterList extends List<DataFilter> {

    default boolean allow(Object item, DataWriter writer, PropertyAccessor propertyAccessor) {
        final boolean[] result = {true};

        forEach(filter -> {
            if (result[0])
                try {
                    if (!filter.allows(item, writer, propertyAccessor))
                        result[0] = false;
                } catch (Exception e) {
                    if (RowWriter.DEBUG)
                        writer.writeError(e);
                    else throw e;
                }
        });

        return result[0];
    }

    // this is the best class I've written in my entire career.
    class DataFilterListImpl extends ArrayList<DataFilter> implements DataFilterList {
    }
}
