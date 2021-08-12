package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.IValueCalculator;
import com.angrysurfer.social.shrapnel.component.field.IFieldSpec;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IDataWriter {

    IValueCalculator getValueCalculator();

    List<IFieldSpec> getFields();

    default IFieldSpec getField(String propertyName) {
        return getFields().stream()
                .filter(field -> field.getPropertyName().equalsIgnoreCase(propertyName))
                .findFirst().orElse(null);
    }

    default int getFieldCount() {
        return getFields().size();
    }

    void writeData(Map<String, Object> outputConfig, Collection<Object> items);

    void writeError(Exception e);
}
