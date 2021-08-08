package com.angrysurfer.social.shrapnel.services.mapping;

import com.angrysurfer.social.shrapnel.component.property.Types;
import com.angrysurfer.social.shrapnel.services.model.DBExport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class HashMapResultSetExtractor implements ResultSetExtractor {

    private final DBExport export;

    boolean useTimeStampForLocalDate = true;

    public HashMapResultSetExtractor(DBExport export) {
        this.export = export;
    }

    @Override
    public Collection extractData(ResultSet rs) throws SQLException, DataAccessException {

        Collection<HashMap<String, Object>> results = new ArrayList();
        while (rs.next()) {
            HashMap<String, Object> values = new HashMap();

            export.getFieldSpecs().forEach(col -> {
                try {
                    switch (col.getType()) {
                        case Types.BOOLEAN:
                            if (Objects.nonNull(rs.getString(col.getPropertyName())))
                                values.put(col.getPropertyName(), rs.getBoolean(col.getPropertyName()));
                            break;

                        case Types.CALENDAR:
                            if (Objects.nonNull(rs.getString(col.getPropertyName()))) {
                                Calendar cal = new GregorianCalendar();
                                cal.setTime(rs.getDate(col.getPropertyName()));
                                values.put(col.getPropertyName(), cal);
                            }
                            break;

                        case Types.DATE:
                            if (Objects.nonNull(rs.getString(col.getPropertyName())))
                                values.put(col.getPropertyName(), new Date(rs.getDate(col.getPropertyName()).getTime()));
                            break;

                        case Types.DOUBLE:
                            if (Objects.nonNull(rs.getString(col.getPropertyName())))
                                values.put(col.getPropertyName(), rs.getDouble(col.getPropertyName()));
                            break;

                        case Types.LOCALDATE:
                            if (Objects.nonNull(rs.getString(col.getPropertyName())))
                                values.put(col.getPropertyName(), rs.getDate(col.getPropertyName()).toLocalDate());
                            break;

                        case Types.LOCALDATETIME:
                            if (Objects.nonNull(rs.getString(col.getPropertyName())))
                                if (useTimeStampForLocalDate)
                                    values.put(col.getPropertyName(), rs.getTimestamp(col.getPropertyName()).toLocalDateTime());
                                else
                                    values.put(col.getPropertyName(), rs.getDate(col.getPropertyName()).getTime());
                            break;

                        case Types.RICHTEXT:
                            if (Objects.nonNull(rs.getString(col.getPropertyName())))
                                values.put(col.getPropertyName(), rs.getString(col.getPropertyName()));
                            break;

                        case Types.STRING:
                            if (Objects.nonNull(rs.getString(col.getPropertyName())))
                                values.put(col.getPropertyName(), rs.getString(col.getPropertyName()));
                            break;
                    }
                } catch (SQLException e) {
                    log.error(e.getMessage(), e);
                }
            });

            results.add(values);
        }

        return results;
    }
}
