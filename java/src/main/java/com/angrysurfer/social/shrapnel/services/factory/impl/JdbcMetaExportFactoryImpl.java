package com.angrysurfer.social.shrapnel.services.factory.impl;

import com.angrysurfer.social.shrapnel.services.ExportRequest;
import com.angrysurfer.social.shrapnel.services.factory.ExportFactory;
import com.angrysurfer.social.shrapnel.services.factory.MetaExportFactory;
import com.angrysurfer.social.shrapnel.services.mapping.HashMapResultSetExtractor;
import com.angrysurfer.social.shrapnel.services.model.DBDataSource;
import com.angrysurfer.social.shrapnel.services.model.DBExport;
import com.angrysurfer.social.shrapnel.services.repository.DataSourceModelRepository;
import com.angrysurfer.social.shrapnel.services.repository.ExportModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Slf4j
@Component
public class JdbcMetaExportFactoryImpl implements MetaExportFactory {

    public final static String SQL_FOLDER = "java/src/main/resources/sql/";

    @Resource
    JdbcTemplate jdbcTemplate;

    @Resource
    DataSourceModelRepository dataSourceModelRepository;

    @Resource
    ExportModelRepository exportModelRepository;

    @Override
    public boolean hasFactory(ExportRequest request) {
        DBExport export = exportModelRepository.findByName(request.getExport());
        return Objects.nonNull(export) && export.isConfigured();
    }

    @Override
    public ExportFactory newInstance(ExportRequest request) {
        final DBExport export = exportModelRepository.findByName(request.getExport());
        final DBDataSource dataSource = dataSourceModelRepository.findByName(request.getExport());

        return new JdbcTemplateExportFactory(request, export) {
            @Override
            public Collection getData() {
                Collection data = Collections.EMPTY_LIST;

                try {
                    String sql = getSQL(dataSource);
                    if (Objects.nonNull(sql))
                        data = (Collection) jdbcTemplate.query(sql, new HashMapResultSetExtractor(export));
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

                return data;
            }
        };
    }

    private String getSQL(DBDataSource dataSource) throws IOException {
        return Files.readString(Path.of(SQL_FOLDER + dataSource.getQuery() + ".sql"));
    }

}
