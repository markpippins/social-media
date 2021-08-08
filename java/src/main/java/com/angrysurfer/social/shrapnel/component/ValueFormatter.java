package com.angrysurfer.social.shrapnel.component;

import com.angrysurfer.social.shrapnel.component.FieldSpec;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static com.angrysurfer.social.shrapnel.component.writer.RowWriter.EMPTY_STRING;

public interface ValueFormatter {

    public final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";
    public final String DEFAULT_TIME_FORMAT = "HH:MM:SS";
    public final String DEFAULT_DATE_TIME_FORMAT = "MM/dd/yyyy HH:MM:SS";

    static SimpleDateFormat defaultDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    static SimpleDateFormat defaultTimeFormat = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
    static SimpleDateFormat defaultDateTimeFormat = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);

    boolean hasFormatFor(FieldSpec col);

    Object calculateValue(FieldSpec field, Object item);

    String formatCalculatedValue(FieldSpec field, Object value);

    default String format(FieldSpec field, Boolean value) {
        return Objects.isNull(value) ? getBooleanNullDefault() : value.toString();
    }

    default String getBooleanNullDefault() {
        return Boolean.FALSE.toString();
    }

    default String format(FieldSpec field, Double value) {
        return Objects.isNull(value) ? Double.toString(0) : value.toString();
    }

    default String format(FieldSpec field, Calendar value) {
        return Objects.isNull(value) ? EMPTY_STRING :
                defaultDateTimeFormat.format(value.getTime());
    }

    default String format(FieldSpec field, Date value) {
        return Objects.isNull(value) ? EMPTY_STRING :
                defaultDateFormat.format(value);
    }

    default String format(FieldSpec field, LocalDate value) {
        return Objects.isNull(value) ? EMPTY_STRING :
                defaultDateFormat.format(Date.from(value.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    default String format(FieldSpec field, LocalDateTime value) {
        return Objects.isNull(value) ? EMPTY_STRING :
                defaultDateTimeFormat.format(Date.from(value.atZone(ZoneId.systemDefault()).toInstant()));
    }

    default String format(FieldSpec field, String value) {
        return Objects.isNull(value) ? EMPTY_STRING : value;
    }

//    String format(DBFieldSpec col, Richtext value);

}