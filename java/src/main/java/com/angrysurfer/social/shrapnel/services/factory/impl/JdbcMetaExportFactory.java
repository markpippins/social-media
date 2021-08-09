package com.angrysurfer.social.shrapnel.services.factory.impl;

import com.angrysurfer.social.shrapnel.services.ExportRequest;
import com.angrysurfer.social.shrapnel.services.factory.ExportFactory;
import com.angrysurfer.social.shrapnel.services.factory.MetaExportFactory;
import com.angrysurfer.social.shrapnel.services.model.DataSource;
import com.angrysurfer.social.shrapnel.services.model.Export;
import com.angrysurfer.social.shrapnel.services.repository.DataSourceRepository;
import com.angrysurfer.social.shrapnel.services.repository.ExportRepository;
import com.angrysurfer.social.shrapnel.services.repository.mapping.HashMapResultSetExtractor;
import com.angrysurfer.social.shrapnel.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Slf4j
@Component
public class JdbcMetaExportFactory implements MetaExportFactory {

    @Resource
    JdbcTemplate jdbcTemplate;

    @Resource
    DataSourceRepository dataSourceRepository;

    @Resource
    ExportRepository exportRepository;

    @Override
    public boolean hasFactory(ExportRequest request) {
        Export export = exportRepository.findByName(request.getName());
        return Objects.nonNull(export) && export.isConfigured();
    }

    @Override
    public ExportFactory newInstance(ExportRequest request) {
        final Export export = exportRepository.findByName(request.getName());
        final DataSource dataSource = dataSourceRepository.findByName(request.getName());

        return new JdbcTemplateExportFactory(request, export) {
            @Override
            public Collection getData() {
                Collection data = Collections.EMPTY_LIST;

                try {
                    String sql = FileUtil.getSQL(dataSource.getQueryName());
                    if (Objects.nonNull(sql))
                        data = (Collection) jdbcTemplate.query(sql, new HashMapResultSetExtractor(export));
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

                return data;
            }
        };
    }

}
