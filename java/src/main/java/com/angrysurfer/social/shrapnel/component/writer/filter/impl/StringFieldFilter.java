package com.angrysurfer.social.shrapnel.component.writer.filter.impl;

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

import static com.angrysurfer.social.shrapnel.component.writer.TableWriter.EMPTY_STRING;

@Getter
@Setter
@Slf4j
public class StringFieldFilter implements DataFilter {

    private static final String NULL = "null";

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
            getFilterCriteria().forEach((propertyName, criteria) -> {
                FieldSpec field = writer.getField(propertyName);
                if (Objects.isNull(criteria) || Objects.isNull(field))
                    throw new IllegalArgumentException();

                String propertyValue = null;

                switch (writer.getField(propertyName).getType()) {
                    case STRING:
                        propertyValue = accessor.getString(item, propertyName);
                }

                if ((Objects.isNull(propertyValue) && !asCriteriaString(criteria).equals(EMPTY_STRING)) ||
                        !compare(propertyValue, criteria))
                    result[0] = false;

                log.info("allows({}) returning {} for criteria {}:{}", Objects.nonNull(propertyValue) ? propertyValue : "''", result[0], propertyName, propertyValue);
            });

        return result[0];
    }

    private String asCriteriaString(Object criteria) {
        return criteria.toString().replace("*", "").toLowerCase().trim();
    }

    private boolean compare(String propertyValue, Object criteria) {
        final String criteriaString = asCriteriaString(criteria);

        if (criteriaString.equalsIgnoreCase(NULL) && propertyValue == null)
            return true;

        if (criteria.toString().startsWith("*") && criteria.toString().endsWith("*"))
            return propertyValue.toLowerCase(Locale.ROOT).contains(criteriaString);

        if (criteria.toString().startsWith("*"))
            return propertyValue.toLowerCase(Locale.ROOT).endsWith(criteriaString);

        if (criteria.toString().endsWith("*"))
            return propertyValue.toLowerCase(Locale.ROOT).startsWith(criteriaString);

        return propertyValue.equalsIgnoreCase(criteriaString);
    }
}
