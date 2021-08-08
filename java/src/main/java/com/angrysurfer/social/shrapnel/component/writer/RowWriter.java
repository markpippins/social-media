package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.ValueFormatter;
import com.angrysurfer.social.shrapnel.component.property.PropertyAccessor;
import com.angrysurfer.social.shrapnel.component.property.ProxyPropertyAccessor;
import com.angrysurfer.social.shrapnel.component.property.Types;
import com.angrysurfer.social.shrapnel.component.property.PropertyUtilsPropertyAccessor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Getter
@Setter
public abstract class RowWriter implements DataWriter, ProxyPropertyAccessor {

    private PropertyAccessor propertyAccessor;

    public static final String EMPTY_STRING = "";

    private List<FieldSpec> fields;

    private ValueFormatter valueFormatter;

    public RowWriter(List<FieldSpec> fields) {
        setPropertyAccessor(new PropertyUtilsPropertyAccessor());
        setFields(fields);
    }

    public RowWriter(List<FieldSpec> fields, ValueFormatter valueFormatter) {
        setPropertyAccessor(new PropertyUtilsPropertyAccessor());
        setFields(fields);
        setValueFormatter(valueFormatter);
    }

    public int getCellOffSet(Object item) {
        return 0;
    }

    public ValueFormatter getValueFormatter() {
        if (Objects.isNull(valueFormatter))
            valueFormatter = new ValueFormatter() {

                @Override
                public boolean hasFormatFor(FieldSpec field) {
                    return false;
                }

                @Override
                public Object calculateValue(FieldSpec field, Object item) {
                    return EMPTY_STRING;
                }

                @Override
                public String formatCalculatedValue(FieldSpec field, Object value) {
                    return EMPTY_STRING;
                }
            };

        return valueFormatter;
    }

    public String getValue(Object item, FieldSpec field) {
        if (accessorExists(item, field.getPropertyName()) || field.isCalculated())
            try {
                switch (field.getType()) {
                    case Types.BOOLEAN:
                        Boolean bool = getBoolean(item, field.getPropertyName());
                        return shouldOnlyFormat(field) ?
                                getValueFormatter().format(field, bool) :
                                extendedGetValue(item, field, bool);

                    case Types.CALENDAR:
                        Calendar calendar = getCalendar(item, field.getPropertyName());
                        return shouldOnlyFormat(field) ?
                                getValueFormatter().format(field, calendar) :
                                extendedGetValue(item, field, calendar);

                    case Types.DATE:
                        Date date = getDate(item, field.getPropertyName());
                        return shouldOnlyFormat(field) ?
                                getValueFormatter().format(field, date) :
                                extendedGetValue(item, field, date);

                    case Types.DOUBLE:
                        Double dbl = getDouble(item, field.getPropertyName());
                        return shouldOnlyFormat(field) ?
                                getValueFormatter().format(field, dbl) :
                                extendedGetValue(item, field, dbl);

                    case Types.LOCALDATE:
                        LocalDate localDate = getLocalDate(item, field.getPropertyName());
                        return shouldOnlyFormat(field) ?
                                getValueFormatter().format(field, localDate) :
                                extendedGetValue(item, field, localDate);

                    case Types.LOCALDATETIME:
                        LocalDateTime localDateTime = getLocalDateTime(item, field.getPropertyName());
                        return shouldOnlyFormat(field) ?
                                getValueFormatter().format(field, localDateTime) :
                                extendedGetValue(item, field, localDateTime);

                    case Types.RICHTEXT:
                        break;

                    case Types.STRING:
                        String string = getString(item, field.getPropertyName());
                        return shouldOnlyFormat(field) ?
                                getValueFormatter().format(field, string) :
                                extendedGetValue(item, field, string);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        return EMPTY_STRING;
    }

    private String extendedGetValue(Object item, FieldSpec field, Object value) {
        return field.isCalculated() && !getValueFormatter().hasFormatFor(field) ?
                getValueFormatter().calculateValue(field, item).toString() :
                getValueFormatter().hasFormatFor(field) && field.isCalculated() ?
                        getValueFormatter().formatCalculatedValue(field, getValueFormatter().calculateValue(field, item)) :
                        Objects.nonNull(value) ? value.toString() : EMPTY_STRING;
    }

    protected boolean shouldOnlyFormat(FieldSpec field) {
        return !field.isCalculated() && getValueFormatter().hasFormatFor(field);
    }

    public boolean shouldSkip(FieldSpec field, Object item) {
        return Objects.isNull(field.getPropertyName());
    }

    public boolean shouldWrite(FieldSpec field, Object item) {
        return Objects.nonNull(field.getPropertyName());
    }
}
