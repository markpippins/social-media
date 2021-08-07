package com.angrysurfer.social.shrapnel.services.factory.impl;

import com.angrysurfer.social.shrapnel.services.ExportRequest;
import com.angrysurfer.social.shrapnel.services.factory.ExporterFactory;
import com.angrysurfer.social.shrapnel.services.factory.ExporterFactoryFactory;
import com.angrysurfer.social.shrapnel.services.model.DataSourceModel;
import com.angrysurfer.social.shrapnel.services.model.ExportModel;
import com.angrysurfer.social.shrapnel.services.repository.DataSourceModelRepository;
import com.angrysurfer.social.shrapnel.services.repository.ExportModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Slf4j
@Service
public class JdbcExporterFactoryFactoryImpl implements ExporterFactoryFactory {

    public final static String SQL_FOLDER = "java/src/main/resources/sql/";

    @Resource
    JdbcTemplate jdbcTemplate;

    @Resource
    DataSourceModelRepository dataSourceModelRepository;

    @Resource
    ExportModelRepository exportModelRepository;

    @Override
    public boolean hasFactory(ExportRequest request) {
        return Objects.nonNull(exportModelRepository.findByName(request.getExport()));
    }

    @Override
    public ExporterFactory newInstance(ExportRequest request) {
        final ExportModel export = exportModelRepository.findByName(request.getExport());
        final DataSourceModel dataSource = dataSourceModelRepository.findByName(request.getExport());
        return new JdbcExporterFactory(request, export) {
            @Override
            public Collection getData() {
                String sql = getSQL(dataSource);
                return Objects.nonNull(sql) ?
                        (Collection) jdbcTemplate.query(sql, new JdbcExporterFactoryResultSetExtractor(export)) :
                        Collections.EMPTY_LIST;
            }
        };
    }

    private String getSQL(DataSourceModel dataSource) {
        String filename = SQL_FOLDER + dataSource.getQuery() + ".sql";
        String result = null;
        try {
            result = Files.readString(Path.of(filename));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

}
