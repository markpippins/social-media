package com.angrysurfer.social.shrapnel.services.factory.impl;

import com.angrysurfer.social.shrapnel.services.ExportRequest;
import com.angrysurfer.social.shrapnel.services.factory.ExporterFactory;
import com.angrysurfer.social.shrapnel.services.factory.JdbcExporterFactoryFactory;
import com.angrysurfer.social.shrapnel.services.model.DataSourceModel;
import com.angrysurfer.social.shrapnel.services.model.ExportModel;
import com.angrysurfer.social.shrapnel.services.repository.DataSourceModelRepository;
import com.angrysurfer.social.shrapnel.services.repository.ExportModelRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Objects;

@Service
public class JdbcExporterFactoryFactoryImpl implements JdbcExporterFactoryFactory {

    @Resource
    JdbcTemplate jdbcTemplate;

    @Resource
    DataSourceModelRepository dataSourceModelRepository;

    @Resource
    ExportModelRepository exportModelRepository;

    @Override
    public boolean hasFactory(ExportRequest request) {
        return Objects.nonNull(exportModelRepository.findByName(request.getExportName()));
    }

    @Override
    public ExporterFactory newInstance(ExportRequest request) {
        final ExportModel export = exportModelRepository.findByName(request.getExportName());
        final DataSourceModel dataSource = dataSourceModelRepository.findByName(request.getExportName());
        return new JdbcExporterFactory(request, export) {
            @Override
            public Collection getData() {
                return (Collection) jdbcTemplate.query(dataSource.getQuery(), new JdbcExporterFactoryResultSetExtractor(export));
            }
        };
    }

}
