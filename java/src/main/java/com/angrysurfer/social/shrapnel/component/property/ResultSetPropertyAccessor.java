package com.angrysurfer.social.shrapnel.component.property;

import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ResultSetPropertyAccessor implements PropertyAccessor {

    @Override
    public boolean accessorExists(Object item, String propertyName) {
        return false;
    }

    @Override
    public Boolean getBoolean(Object item, String propertyName) {
        try {
            return ((ResultSet) item).getBoolean(propertyName);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public Double getDouble(Object item, String propertyName) {
        try {
            return ((ResultSet) item).getDouble(propertyName);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public Calendar getCalendar(Object item, String propertyName) {
        try {
            Date date = ((ResultSet) item).getDate(propertyName);

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public Date getDate(Object item, String propertyName) {
        try {
            return ((ResultSet) item).getDate(propertyName);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public LocalDate getLocalDate(Object item, String propertyName) {
        try {
            ((ResultSet) item).getDate(propertyName);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public LocalDateTime getLocalDateTime(Object item, String propertyName) {
        try {
            ((ResultSet) item).getDate(propertyName);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public String getString(Object item, String propertyName) {
        try {
            ((ResultSet) item).getString(propertyName);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public Set<String> getPropertyNames(Object item) {
        Set<String> properties = new HashSet();
        try {
            ResultSetMetaData meta = ((ResultSet) item).getMetaData();
            for (int i = 1; i < meta.getColumnCount(); i++)
                properties.add(meta.getColumnName(i));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return properties;
    }

    @Override
    public void inspect(Object value) {

    }
}
