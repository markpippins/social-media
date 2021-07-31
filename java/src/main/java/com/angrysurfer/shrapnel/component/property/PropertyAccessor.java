package com.angrysurfer.shrapnel.component.property;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface PropertyAccessor {

    Boolean getBooleanValue(Object item, String propertyName);

    Double getDoubleValue(Object item, String propertyName);

    Calendar getCalendarValue(Object item, String propertyName);

    Date getDateValue(Object item, String propertyName);

    LocalDate getLocalDateValue(Object item, String propertyName);

    LocalDateTime getLocalDateTimeValue(Object item, String propertyName);

    //    RichText getRichTextValue(Object item, String propertyName);
    String getStringValue(Object item, String propertyName);

    List<String> getPropertyNames(Object item);

    boolean valueExists(Object item, String propertyName);

    void inspect(Object value);
}
