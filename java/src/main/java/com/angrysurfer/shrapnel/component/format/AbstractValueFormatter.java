package com.angrysurfer.shrapnel.component.format;

import com.angrysurfer.shrapnel.component.property.ColumnSpec;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public abstract class AbstractValueFormatter implements ValueFormatter {
    @Override
    public String format(ColumnSpec col, Boolean value) {
        return Objects.isNull(value) ? getBooleanNullDefault() : value.toString();
    }

    private String getBooleanNullDefault() {
        return Boolean.FALSE.toString();
    }

    @Override
    public String format(ColumnSpec col, Double value) {
        return Objects.isNull(value) ? Double.toString(0) : value.toString();
    }

    @Override
    public String format(ColumnSpec col, Calendar value) {
        return Objects.isNull(value) ? "" : value.toString();
    }

    @Override
    public String format(ColumnSpec col, Date value) {
        return Objects.isNull(value) ? "" : value.toString();
    }

    @Override
    public String format(ColumnSpec col, LocalDate value) {
        return Objects.isNull(value) ? "" : value.toString();
    }

    @Override
    public String format(ColumnSpec col, LocalDateTime value) {
        return Objects.isNull(value) ? "" : value.toString();
    }

    @Override
    public String format(ColumnSpec col, String value) {
        return Objects.isNull(value) ? "" : value.toString();
    }
}
