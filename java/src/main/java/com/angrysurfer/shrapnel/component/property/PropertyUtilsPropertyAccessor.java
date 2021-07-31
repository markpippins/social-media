package com.angrysurfer.shrapnel.component.property;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class PropertyUtilsPropertyAccessor implements PropertyAccessor {
    @Override
    public Boolean getBooleanValue(Object item, String propertyName) {
        Boolean result = null;

        try {
            PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(item, propertyName);
            if (Objects.nonNull(desc) && Objects.nonNull(desc.getReadMethod())) {
                Object value = desc.getReadMethod().invoke(item);
                if (Objects.nonNull(value))
                    if (value instanceof Boolean)
                        return (Boolean) value;
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Double getDoubleValue(Object item, String propertyName) {
        Double result = null;

        try {
            PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(item, propertyName);
            if (Objects.nonNull(desc) && Objects.nonNull(desc.getReadMethod())) {
                Object value = desc.getReadMethod().invoke(item);
                if (Objects.nonNull(value))
                    if (value instanceof Double)
                        return (Double) value;
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Calendar getCalendarValue(Object item, String propertyName) {
        Calendar result = null;

        try {
            PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(item, propertyName);
            if (Objects.nonNull(desc) && Objects.nonNull(desc.getReadMethod())) {
                Object value = desc.getReadMethod().invoke(item);
                if (Objects.nonNull(value))
                    if (value instanceof Calendar)
                        return (Calendar) value;
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Date getDateValue(Object item, String propertyName) {
        Date result = null;

        try {
            PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(item, propertyName);
            if (Objects.nonNull(desc) && Objects.nonNull(desc.getReadMethod())) {
                Object value = desc.getReadMethod().invoke(item);
                if (Objects.nonNull(value))
                    if (value instanceof Date)
                        return (Date) value;
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public LocalDate getLocalDateValue(Object item, String propertyName) {
        LocalDate result = null;

        try {
            PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(item, propertyName);
            if (Objects.nonNull(desc) && Objects.nonNull(desc.getReadMethod())) {
                Object value = desc.getReadMethod().invoke(item);
                if (Objects.nonNull(value))
                    if (value instanceof LocalDate)
                        return (LocalDate) value;
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public LocalDateTime getLocalDateTimeValue(Object item, String propertyName) {
        LocalDateTime result = null;

        try {
            PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(item, propertyName);
            if (Objects.nonNull(desc) && Objects.nonNull(desc.getReadMethod())) {
                Object value = desc.getReadMethod().invoke(item);
                if (Objects.nonNull(value))
                    if (value instanceof LocalDateTime)
                        return (LocalDateTime) value;
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public String getStringValue(Object item, String propertyName) {
        String result = null;

        try {
            PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(item, propertyName);
            if (Objects.nonNull(desc) && Objects.nonNull(desc.getReadMethod())) {
                Object value = desc.getReadMethod().invoke(item);
                if (Objects.nonNull(value))
                    return value.toString();
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<String> getPropertyNames(Object item) {
        return Arrays.stream(PropertyUtils.getPropertyDescriptors(item)).map(desc -> desc.getName()).collect(Collectors.toList());
    }

    @Override
    public boolean valueExists(Object item, String propertyName) {
        boolean result = false;

        try {
            PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(item, propertyName);
            result = Objects.nonNull(desc) && Objects.nonNull(desc.getReadMethod());
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public void inspect(Object value) {

    }
}
