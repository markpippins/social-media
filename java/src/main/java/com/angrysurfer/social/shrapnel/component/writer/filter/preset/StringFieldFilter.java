package com.angrysurfer.social.shrapnel.component.writer.filter.preset;

import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.property.PropertyAccessor;
import com.angrysurfer.social.shrapnel.component.writer.DataWriter;
import com.angrysurfer.social.shrapnel.component.writer.filter.DataFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@Slf4j
public class StringFieldFilter implements DataFilter {

    private Map<String, Object> filterCriteria = new HashMap<>();

    public StringFieldFilter() {
    }

    public StringFieldFilter(Map<String, Object> filterCriteria) {
        setFilterCriteria(filterCriteria);
    }

    @Override
    public boolean allows(Object item, DataWriter writer, PropertyAccessor accessor) {
        boolean[] result = {Objects.nonNull(item)};

        if (result[0])
            getFilterCriteria().forEach((propertyName, criteriaValue) -> {
                FieldSpec field = writer.getField(propertyName);
                if (Objects.isNull(criteriaValue) || Objects.isNull(field))
                    throw new NullPointerException();

                String propertyValue = null;

                switch (writer.getField(propertyName).getType()) {
                    case STRING:
                        propertyValue = accessor.getString(item, propertyName);
                }

                if (Objects.isNull(propertyValue) ||
                        !compare(propertyValue, criteriaValue))
                    result[0] = false;

                log.info("allows({}) returning {} for criteria {}:{}", Objects.nonNull(propertyValue) ? propertyValue : "''", result[0], propertyName, propertyValue);
            });

        return result[0];
    }

    private boolean compare(String propertyValue, Object criteriaValue) {
        String param = criteriaValue.toString();
        String value = criteriaValue.toString().replace("*", "").toLowerCase();

        if (param.startsWith("*") && param.endsWith("*"))
            return propertyValue.toLowerCase(Locale.ROOT).contains(value);

        if (param.startsWith("*"))
            return propertyValue.toLowerCase(Locale.ROOT).endsWith(value);

        if (param.endsWith("*"))
            return propertyValue.toLowerCase(Locale.ROOT).startsWith(value);

        return propertyValue.equalsIgnoreCase(value);
    }
}
