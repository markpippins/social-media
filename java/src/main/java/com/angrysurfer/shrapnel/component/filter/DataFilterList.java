package com.angrysurfer.shrapnel.component.filter;

import com.angrysurfer.shrapnel.component.property.PropertyAccessor;
import com.angrysurfer.shrapnel.component.writer.RowWriter;

import java.util.ArrayList;
import java.util.List;

public interface DataFilterList extends List<DataFilter> {

    default boolean allow(Object item, RowWriter writer, PropertyAccessor propertyAccessor) {
        final boolean[] result = {true};

        forEach(filter -> {
            if (!filter.allows(item, writer, propertyAccessor))
                result[0] = false;
        });

        return result[0];
    }

    // this is the best class I've written in my entire career.
    class DataFilterListImpl extends ArrayList<DataFilter> implements DataFilterList {
    }
}
