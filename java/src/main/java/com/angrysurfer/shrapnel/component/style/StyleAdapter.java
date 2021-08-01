package com.angrysurfer.shrapnel.component.style;

import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Cell;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class StyleAdapter extends Style {

    final int MAX_PROPERTY_INDEX = 250;

    private final Map<Integer, Object> extendedProperties = new HashMap<>();

    public void absorb(Style style) {
        for (int index = 0; index < MAX_PROPERTY_INDEX; index++)
            if (style.hasProperty(index))
                try {
                    setProperty(index, style.getProperty(index));
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

        if (style instanceof StyleAdapter) {
            StyleAdapter adapter = (StyleAdapter) style;
            for (int index = 0; index < MAX_PROPERTY_INDEX; index++)
                if (adapter.hasExtendedProperty(index))
                    try {
                        setProperty(index, adapter.getExtendedProperty(index));
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
        }
    }

    public void apply(Cell cell) {
        for (int index = 0; index < MAX_PROPERTY_INDEX; index++)
            if (hasProperty(index))
                try {
                    cell.setProperty(index, getProperty(index));
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
    }

    public boolean hasExtendedProperty(int property) {
        return extendedProperties.containsKey(property);
    }

    public Object getExtendedProperty(int property) {
        return hasExtendedProperty(property) ? extendedProperties.get(property) : null;
    }

    public void setExtendedProperty(int property, Object value) {
        if (Objects.nonNull(value))
            extendedProperties.put(property, value);
        else if (hasExtendedProperty(property))
            extendedProperties.remove(property);
    }
}
