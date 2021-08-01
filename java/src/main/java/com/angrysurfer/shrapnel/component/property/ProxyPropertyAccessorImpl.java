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
    public Boolean getBoolean(Object item, String propertyName) {
        return getPropertyAccessor().getBoolean(item, propertyName);
    }

    @Override
    public Double getDouble(Object item, String propertyName) {
        return getPropertyAccessor().getDouble(item, propertyName);
    }

    @Override
    public Calendar getCalendar(Object item, String propertyName) {
        return getPropertyAccessor().getCalendar(item, propertyName);
    }

    @Override
    public Date getDate(Object item, String propertyName) {
        return getPropertyAccessor().getDate(item, propertyName);
    }

    @Override
    public LocalDate getLocalDate(Object item, String propertyName) {
        return getPropertyAccessor().getLocalDate(item, propertyName);
    }

    @Override
    public LocalDateTime getLocalDateTime(Object item, String propertyName) {
        return getPropertyAccessor().getLocalDateTime(item, propertyName);
    }

    @Override
    public String getString(Object item, String propertyName) {
        return getPropertyAccessor().getString(item, propertyName);
    }

    @Override
    public List<String> getPropertyNames(Object item) {
        return getPropertyAccessor().getPropertyNames(item);
    }

    @Override
    public boolean accessorExists(Object item, String propertyName) {
        return getPropertyAccessor().accessorExists(item, propertyName);
    }

    @Override
    public void inspect(Object value) {
        getPropertyAccessor().inspect(value);
    }
}
