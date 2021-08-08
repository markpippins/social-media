package com.angrysurfer.social.shrapnel.component.format;

import com.angrysurfer.social.shrapnel.component.FieldSpec;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public abstract class AbstractValueFormatter implements ValueFormatter {
    @Override
    public String format(FieldSpec field, Boolean value) {
        return Objects.isNull(value) ? getBooleanNullDefault() : value.toString();
    }

    private String getBooleanNullDefault() {
        return Boolean.FALSE.toString();
    }

    @Override
    public String format(FieldSpec field, Double value) {
        return Objects.isNull(value) ? Double.toString(0) : value.toString();
    }

    @Override
    public String format(FieldSpec field, Calendar value) {
        return nonNullString(value);
    }

    @Override
    public String format(FieldSpec field, Date value) {
        return nonNullString(value);
    }

    @Override
    public String format(FieldSpec field, LocalDate value) {
        return nonNullString(value);
    }

    @Override
    public String format(FieldSpec field, LocalDateTime value) {
        return nonNullString(value);
    }

    @Override
    public String format(FieldSpec field, String value) {
        return nonNullString(value);
    }

    private String nonNullString(Object value) {
        return Objects.isNull(value) ? "" : value.toString();
    }

}
