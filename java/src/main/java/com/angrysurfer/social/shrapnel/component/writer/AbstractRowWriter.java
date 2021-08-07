package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.ColumnSpec;
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
public abstract class AbstractRowWriter extends ProxyPropertyAccessorImpl implements RowWriter {

    static final String EMPTY_STRING = "";

    private List<ColumnSpec> columns;

    private ValueFormatter valueFormatter;

    public AbstractRowWriter(List<ColumnSpec> columns) {
        setPropertyAccessor(new PropertyUtilsPropertyAccessor());
        setColumns(columns);
    }

    public AbstractRowWriter(List<ColumnSpec> columns, ValueFormatter valueFormatter) {
        setPropertyAccessor(new PropertyUtilsPropertyAccessor());
        setColumns(columns);
        setValueFormatter(valueFormatter);
    }

    public boolean accessorExists(Object item, String propertyName) {
        return super.accessorExists(item, propertyName);
    }

    public int getCellOffSet(Object item) {
        return 0;
    }

    public int getColumnCount() {
        return getColumns().size();
    }

    public ValueFormatter getValueFormatter() {
        if (Objects.isNull(valueFormatter))
            valueFormatter = new AbstractValueFormatter() {

                @Override
                public boolean hasFormatFor(ColumnSpec col) {
                    return false;
                }
            };

        return valueFormatter;
    }

    public String getFormattedValue(Object item, ColumnSpec col) {
        if (accessorExists(item, col.getPropertyName()))
            try {
                switch (col.getType()) {
                    case Types.BOOLEAN:
                        Boolean bool = getBoolean(item, col.getPropertyName());
                        if (Objects.nonNull(bool))
                            return getValueFormatter().format(col, bool);
                        break;

                    case Types.CALENDAR:
                        Calendar calendar = getCalendar(item, col.getPropertyName());
                        if (Objects.nonNull(calendar))
                            return getValueFormatter().format(col, calendar);
                        break;

                    case Types.DATE:
                        Date date = getDate(item, col.getPropertyName());
                        if (Objects.nonNull(date))
                            return getValueFormatter().format(col, date);
                        break;

                    case Types.DOUBLE:
                        Double dbl = getDouble(item, col.getPropertyName());
                        if (Objects.nonNull(dbl))
                            return getValueFormatter().format(col, dbl);
                        break;

                    case Types.LOCALDATE:
                        LocalDate localDate = getLocalDate(item, col.getPropertyName());
                        if (Objects.nonNull(localDate))
                            return getValueFormatter().format(col, localDate);
                        break;

                    case Types.LOCALDATETIME:
                        LocalDateTime localDateTime = getLocalDateTime(item, col.getPropertyName());
                        if (Objects.nonNull(localDateTime))
                            return getValueFormatter().format(col, localDateTime);
                        break;

                    case Types.RICHTEXT:
//                        Boolean value = getBooleanValue(item, col.getPropertyName();
//                        if (Objects.nonNull(value))
//                            return getValueFormatter().format(col, value.toString();
                        break;

                    case Types.STRING:
                        String value = getString(item, col.getPropertyName());
                        if (Objects.nonNull(value))
                            return getValueFormatter().format(col, value);
                        break;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        return EMPTY_STRING;
    }

    public String getValue(Object item, ColumnSpec col) {
        if (accessorExists(item, col.getPropertyName()))
            try {
                switch (col.getType()) {
                    case Types.BOOLEAN:
                        Boolean bool = getBoolean(item, col.getPropertyName());
                        if (Objects.nonNull(bool))
                            return bool.toString();
                        break;

                    case Types.CALENDAR:
                        Calendar calendar = getCalendar(item, col.getPropertyName());
                        if (Objects.nonNull(calendar))
                            return calendar.toString();
                        break;

                    case Types.DATE:
                        Date date = getDate(item, col.getPropertyName());
                        if (Objects.nonNull(date))
                            return date.toString();
                        break;

                    case Types.DOUBLE:
                        Double dbl = getDouble(item, col.getPropertyName());
                        if (Objects.nonNull(dbl))
                            return dbl.toString();
                        break;

                    case Types.LOCALDATE:
                        LocalDate localDate = getLocalDate(item, col.getPropertyName());
                        if (Objects.nonNull(localDate))
                            return localDate.toString();
                        break;

                    case Types.LOCALDATETIME:
                        LocalDateTime localDateTime = getLocalDateTime(item, col.getPropertyName());
                        if (Objects.nonNull(localDateTime))
                            return localDateTime.toString();
                        break;

                    case Types.RICHTEXT:
//                        Boolean value = getBooleanValue(item, col.getPropertyName());
//                        if (Objects.nonNull(value))
//                            return value.toString());
                        break;

                    case Types.STRING:
                        String value = getString(item, col.getPropertyName());
                        if (Objects.nonNull(value))
                            return value.toString();
                        break;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        return EMPTY_STRING;
    }

    public boolean shouldSkip(ColumnSpec col, Object item) {
        return Objects.isNull(col.getPropertyName());
    }

    public boolean shouldWrite(ColumnSpec col, Object item) {
        return Objects.nonNull(col.getPropertyName());
    }
}
