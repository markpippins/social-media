package com.angrysurfer.shrapnel.component.property;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProxyPropertyAccessorImpl implements ProxyPropertyAccessor {

    private PropertyAccessor propertyAccessor;

    @Override
    public Boolean getBooleanValue(Object item, String propertyName) {
        return getPropertyAccessor().getBooleanValue(item, propertyName);
    }

    @Override
    public Double getDoubleValue(Object item, String propertyName) {
        return getPropertyAccessor().getDoubleValue(item, propertyName);
    }

    @Override
    public Calendar getCalendarValue(Object item, String propertyName) {
        return getPropertyAccessor().getCalendarValue(item, propertyName);
    }

    @Override
    public Date getDateValue(Object item, String propertyName) {
        return getPropertyAccessor().getDateValue(item, propertyName);
    }

    @Override
    public LocalDate getLocalDateValue(Object item, String propertyName) {
        return getPropertyAccessor().getLocalDateValue(item, propertyName);
    }

    @Override
    public LocalDateTime getLocalDateTimeValue(Object item, String propertyName) {
        return getPropertyAccessor().getLocalDateTimeValue(item, propertyName);
    }

    @Override
    public String getStringValue(Object item, String propertyName) {
        return getPropertyAccessor().getStringValue(item, propertyName);
    }

    @Override
    public List<String> getPropertyNames(Object item) {
        return getPropertyAccessor().getPropertyNames(item);
    }

    @Override
    public boolean valueExists(Object item, String propertyName) {
        return getPropertyAccessor().valueExists(item, propertyName);
    }

    @Override
    public void inspect(Object value) {
        getPropertyAccessor().inspect(value);
    }
}
