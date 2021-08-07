package com.angrysurfer.social.shrapnel.service.impl;

import com.angrysurfer.social.shrapnel.service.ExportRequest;
import com.angrysurfer.social.shrapnel.service.ExporterFactory;
import com.angrysurfer.social.shrapnel.service.ExporterFactoryTemplateService;
import com.angrysurfer.social.shrapnel.service.model.DataSourceModel;
import com.angrysurfer.social.shrapnel.service.model.ExportModel;
import com.angrysurfer.social.shrapnel.service.repository.ColumnSpecModelRepository;
import com.angrysurfer.social.shrapnel.service.repository.DataSourceModelRepository;
import com.angrysurfer.social.shrapnel.service.repository.ExportModelRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class ExporterFactoryTemplateServiceImpl implements ExporterFactoryTemplateService {

    @Resource
    ColumnSpecModelRepository columnSpecModelRepository;

    @Resource
    DataSourceModelRepository dataSourceModelRepository;

    @Resource
    ExportModelRepository exportModelRepository;

    @Override
    public boolean canExport(ExportRequest request) {
        ExportModel export = exportModelRepository.findByName(request.getExportName());
        return Objects.nonNull(export);
    }

    @Override
    public ExporterFactory getJdbcTemplateFactory(ExportRequest request) {
        DataSourceModel dataSource = dataSourceModelRepository.findByName(request.getExportName());
        ExportModel export = exportModelRepository.findByName(request.getExportName());
        return new JdbcTemplateExporterFactoryImpl(request, export, dataSource);
    }

}
