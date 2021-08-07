package com.angrysurfer.social.shrapnel.service.factory;

import com.angrysurfer.social.shrapnel.Exporter;
import com.angrysurfer.social.shrapnel.TableExporter;
import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.component.property.HashMapPropertyAccessor;
import com.angrysurfer.social.shrapnel.service.ExportRequest;
import com.angrysurfer.social.shrapnel.service.factory.ExporterFactory;
import com.angrysurfer.social.shrapnel.service.mapping.ColumnSpecMapper;
import com.angrysurfer.social.shrapnel.service.model.ExportModel;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public abstract class JdbcExporterFactory implements ExporterFactory {
    private ExportRequest request;
    private ExportModel exportModel;

    public JdbcExporterFactory(ExportRequest request, ExportModel exportModel) {
        this.request = request;
        this.exportModel = exportModel;
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
        TableExporter exporter = new TableExporter(getExportName(), columns);
        exporter.setPropertyAccessor(new HashMapPropertyAccessor());
        return exporter;
    }

}
