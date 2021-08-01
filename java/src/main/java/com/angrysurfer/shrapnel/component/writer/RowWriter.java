package com.angrysurfer.shrapnel.component.writer;

import com.angrysurfer.shrapnel.component.property.ColumnSpec;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RowWriter {

    boolean DEBUG = false;

    List<ColumnSpec> getColumns();

    void writeValues(Map<String, Object> outputConfig, Collection<Object> items);

    default ColumnSpec getColumn(String propertyName) {
        Optional<ColumnSpec> opt = getColumns().stream().filter(c -> c.getPropertyName().equalsIgnoreCase(propertyName)).findFirst();
        return opt.isPresent() ? opt.get() : null;
    }
}
