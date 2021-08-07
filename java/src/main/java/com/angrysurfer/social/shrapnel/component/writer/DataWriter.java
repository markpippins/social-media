package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.FieldSpec;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DataWriter {

    boolean DEBUG = false;

    List<FieldSpec> getFields();

    void run(Map<String, Object> outputConfig, Collection<Object> items);

    default FieldSpec getField(String propertyName) {
        Optional<FieldSpec> opt = getFields().stream().filter(c -> c.getPropertyName().equalsIgnoreCase(propertyName)).findFirst();
        return opt.isPresent() ? opt.get() : null;
    }
}
