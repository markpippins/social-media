package com.angrysurfer.social.shrapnel.component.writer.filter;

import com.angrysurfer.social.shrapnel.component.property.PropertyAccessor;
import com.angrysurfer.social.shrapnel.component.writer.DataWriter;
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
public class StringStartsWithFilter implements DataFilter {

    private Map<String, Object> filterCriteria = new HashMap<>();

    public StringStartsWithFilter() {
    }

    public StringStartsWithFilter(Map<String, Object> filterCriteria) {
        setFilterCriteria(filterCriteria);
    }

    @Override
    public boolean allows(Object item, DataWriter writer, PropertyAccessor accessor) {
        boolean[] result = {Objects.nonNull(item)};

        if (result[0])
            getFilterCriteria().forEach((propertyName, criteriaValue) -> {
                if (Objects.isNull(criteriaValue) || Objects.isNull(writer.getField(propertyName)))
                    throw new NullPointerException();

                String propertyValue = null;

                switch (writer.getField(propertyName).getType()) {
                    case STRING:
                        propertyValue = accessor.getString(item, propertyName);
                }

                if (Objects.isNull(propertyValue) ||
                        !propertyValue.toLowerCase(Locale.ROOT).startsWith(criteriaValue.toString().toLowerCase(Locale.ROOT)))
                    result[0] = false;

                log.info("allow({}) returning {}", propertyName, result[0]);
            });

        return result[0];
    }
}
