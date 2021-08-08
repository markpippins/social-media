package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.format.AbstractValueFormatter;
import com.angrysurfer.social.shrapnel.component.format.ValueFormatter;
import com.angrysurfer.social.shrapnel.component.property.PropertyUtilsPropertyAccessor;
import com.angrysurfer.social.shrapnel.component.property.ProxyPropertyAccessorImpl;
import com.angrysurfer.social.shrapnel.component.property.Types;
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
public abstract class RowWriter extends ProxyPropertyAccessorImpl implements DataWriter {

    static final String EMPTY_STRING = "";

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

    public int getFieldCount() {
        return getFields().size();
    }

    public ValueFormatter getValueFormatter() {
        if (Objects.isNull(valueFormatter))
            valueFormatter = new AbstractValueFormatter() {

                @Override
                public boolean hasFormatFor(FieldSpec field) {
                    return false;
                }

                @Override
                public String calculateValue(FieldSpec field, Object item) {
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
                ValueFormatter formatter = getValueFormatter();
                switch (field.getType()) {
                    case Types.BOOLEAN:
                        Boolean bool = getBoolean(item, field.getPropertyName());
                        return !field.isCalculated() && formatter.hasFormatFor(field) ?
                                formatter.format(field, bool) : subGetValue(item, field, bool);

                    case Types.CALENDAR:
                        Calendar calendar = getCalendar(item, field.getPropertyName());
                        return !field.isCalculated() && formatter.hasFormatFor(field) ?
                                formatter.format(field, calendar) : subGetValue(item, field, calendar);

                    case Types.DATE:
                        Date date = getDate(item, field.getPropertyName());
                        return !field.isCalculated() && formatter.hasFormatFor(field) ?
                                formatter.format(field, date) : subGetValue(item, field, date);

                    case Types.DOUBLE:
                        Double dbl = getDouble(item, field.getPropertyName());
                        return !field.isCalculated() && formatter.hasFormatFor(field) ?
                                formatter.format(field, dbl) : subGetValue(item, field, dbl);

                    case Types.LOCALDATE:
                        LocalDate localDate = getLocalDate(item, field.getPropertyName());
                        return !field.isCalculated() && formatter.hasFormatFor(field) ?
                                formatter.format(field, localDate) : subGetValue(item, field, localDate);

                    case Types.LOCALDATETIME:
                        LocalDateTime localDateTime = getLocalDateTime(item, field.getPropertyName());
                        return !field.isCalculated() && formatter.hasFormatFor(field) ?
                                formatter.format(field, localDateTime) : subGetValue(item, field, localDateTime);

                    case Types.RICHTEXT:
                        break;

                    case Types.STRING:
                        String string = getString(item, field.getPropertyName());
                        return !field.isCalculated() && formatter.hasFormatFor(field) ?
                                formatter.format(field, string) : subGetValue(item, field, string);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        return EMPTY_STRING;
    }

    private String subGetValue(Object item, FieldSpec field, Object value) {
        ValueFormatter formatter = getValueFormatter();
        return !formatter.hasFormatFor(field) && field.isCalculated() ? formatter.calculateValue(field, item) :
                formatter.hasFormatFor(field) && field.isCalculated() ? formatter.formatCalculatedValue(field, formatter.calculateValue(field, item)) :
                        Objects.isNull(value) ? EMPTY_STRING : value.toString();
    }

    public boolean shouldSkip(FieldSpec field, Object item) {
        return Objects.isNull(field.getPropertyName());
    }

    public boolean shouldWrite(FieldSpec field, Object item) {
        return Objects.nonNull(field.getPropertyName());
    }
}
