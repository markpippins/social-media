package com.angrysurfer.social.shrapnel.service.factory;

import com.angrysurfer.social.shrapnel.component.property.Types;
import com.angrysurfer.social.shrapnel.service.model.ExportModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

class JdbcExporterFactoryResultSetExtractor implements ResultSetExtractor {

    private final ExportModel export;

    public JdbcExporterFactoryResultSetExtractor(ExportModel export) {
        this.export = export;
    }

    @Override
    public Collection extractData(ResultSet rs) throws SQLException, DataAccessException {
        Collection<HashMap<String, Object>> results = new ArrayList();
        while (rs.next()) {
            HashMap<String, Object> values = new HashMap();
            export.getColumnSpecs().forEach(col -> {
                try {
                    switch (col.getType()) {
                        case Types.BOOLEAN:
                            values.put(col.getPropertyName(), rs.getBoolean(col.getPropertyName()));
                            break;

                        case Types.DATE:
                            values.put(col.getPropertyName(), rs.getDate(col.getPropertyName()));
                            break;

                        case Types.DOUBLE:
                            values.put(col.getPropertyName(), rs.getDouble(col.getPropertyName()));
                            break;

                        case Types.STRING:
                            values.put(col.getPropertyName(), rs.getString(col.getPropertyName()));
                            break;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            results.add(values);
        }
        return results;
    }
}
