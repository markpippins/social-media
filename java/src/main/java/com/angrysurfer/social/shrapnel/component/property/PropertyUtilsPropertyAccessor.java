package com.angrysurfer.social.shrapnel.component.property;

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
    public boolean accessorExists(Object item, String propertyName) {
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
    public Boolean getBoolean(Object item, String propertyName) {
        try {
            PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(item, propertyName);
            if (Objects.nonNull(desc) && Objects.nonNull(desc.getReadMethod())) {
                Object value = desc.getReadMethod().invoke(item);
                if (Objects.nonNull(value) && value instanceof Boolean)
                    return (Boolean) value;
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Calendar getCalendar(Object item, String propertyName) {
        try {
            PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(item, propertyName);
            if (Objects.nonNull(desc) && Objects.nonNull(desc.getReadMethod())) {
                Object value = desc.getReadMethod().invoke(item);
                if (Objects.nonNull(value) && value instanceof Calendar)
                    return (Calendar) value;
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Date getDate(Object item, String propertyName) {
        try {
            PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(item, propertyName);
            if (Objects.nonNull(desc) && Objects.nonNull(desc.getReadMethod())) {
                Object value = desc.getReadMethod().invoke(item);
                if (Objects.nonNull(value) && value instanceof Date)
                    return (Date) value;
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Double getDouble(Object item, String propertyName) {
        try {
            PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(item, propertyName);
            if (Objects.nonNull(desc) && Objects.nonNull(desc.getReadMethod())) {
                Object value = desc.getReadMethod().invoke(item);
                if (Objects.nonNull(value) && value instanceof Double)
                    return (Double) value;
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public LocalDate getLocalDate(Object item, String propertyName) {
        try {
            PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(item, propertyName);
            if (Objects.nonNull(desc) && Objects.nonNull(desc.getReadMethod())) {
                Object value = desc.getReadMethod().invoke(item);
                if (Objects.nonNull(value) && value instanceof LocalDate)
                    return (LocalDate) value;
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public LocalDateTime getLocalDateTime(Object item, String propertyName) {
        try {
            PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(item, propertyName);
            if (Objects.nonNull(desc) && Objects.nonNull(desc.getReadMethod())) {
                Object value = desc.getReadMethod().invoke(item);
                if (Objects.nonNull(value) && value instanceof LocalDateTime)
                    return (LocalDateTime) value;
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String getString(Object item, String propertyName) {
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
        return null;
    }

    @Override
    public Set<String> getPropertyNames(Object item) {
        return Arrays.stream(PropertyUtils.getPropertyDescriptors(item)).map(desc -> desc.getName()).collect(Collectors.toSet());
    }

    @Override
    public void inspect(Object item) {
        getPropertyNames(item).forEach(propertyName -> {
            log.info(propertyName + " = " + getString(item, propertyName));
        });
    }
}
