package com.angrysurfer.social.shrapnel.service.impl;

import com.angrysurfer.social.shrapnel.Exporter;
import com.angrysurfer.social.shrapnel.TableExporter;
import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.service.ExportRequest;
import com.angrysurfer.social.shrapnel.service.ExporterFactory;
import com.angrysurfer.social.shrapnel.service.mapping.ColumnSpecMapper;
import com.angrysurfer.social.shrapnel.service.model.DataSourceModel;
import com.angrysurfer.social.shrapnel.service.model.ExportModel;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
class JdbcTemplateExporterFactoryImpl implements ExporterFactory {
    private DataSourceModel dataSource;
    private ExportRequest request;
    private ExportModel exportModel;

    public JdbcTemplateExporterFactoryImpl(ExportRequest request, ExportModel exportModel, DataSourceModel dataSource) {
        this.request = request;
        this.exportModel = exportModel;
        this.dataSource = dataSource;
    }

    @Override
    public Collection<Object> getData() {
        return Objects.nonNull(dataSource) ? dataSource.getData() : Collections.EMPTY_LIST;
    }

    @Override
    public String getExportName() {
        return request.getExportName();
    }

    @Override
    public Class getExportClass() {
        return TableExporter.class;
    }

    @Override
    public Exporter newInstance() {
        List<ColumnSpec> columns = getExportModel().getColumnSpecs().stream().map(col -> ColumnSpecMapper.create(col)).collect(Collectors.toList());
        return new TableExporter(getExportName(), columns);
    }

}
