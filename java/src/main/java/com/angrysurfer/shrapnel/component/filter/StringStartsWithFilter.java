package com.angrysurfer.shrapnel.component.filter;

import com.angrysurfer.shrapnel.component.property.Types;
import com.angrysurfer.shrapnel.component.property.ColumnSpec;
import com.angrysurfer.shrapnel.component.property.PropertyAccessor;
import com.angrysurfer.shrapnel.component.writer.RowWriter;
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
    public boolean allows(Object item, RowWriter writer, PropertyAccessor accessor) {
        boolean[] result = {true};

        getFilterCriteria().forEach((propertyName, criteriaValue) -> {
            if (Objects.isNull(criteriaValue) || Objects.isNull(writer.getColumn(propertyName)))
                throw new NullPointerException();

            ColumnSpec col = writer.getColumn(propertyName);
            switch (col.getType()) {
                case Types.STRING:
                    String propertyValue = accessor.getString(item, propertyName);
                    if (Objects.isNull(propertyValue) || !propertyValue.toUpperCase(Locale.ROOT).startsWith(criteriaValue.toString().toUpperCase(Locale.ROOT)))
                        result[0] = false;
            }

            log.info("allow({} returning {}", propertyName, result[0]);
        });
        return result[0];
    }
}
