package com.angrysurfer.social.shrapnel.services.factory.impl;

import com.angrysurfer.social.shrapnel.Exporter;
import com.angrysurfer.social.shrapnel.TableExporter;
import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.property.HashMapPropertyAccessor;
import com.angrysurfer.social.shrapnel.services.ExportRequest;
import com.angrysurfer.social.shrapnel.services.factory.ExporterFactory;
import com.angrysurfer.social.shrapnel.services.mapping.FieldSpecMapper;
import com.angrysurfer.social.shrapnel.services.model.FieldSpecModel;
import com.angrysurfer.social.shrapnel.services.model.ExportModel;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public abstract class JdbcTemplateExporterFactory implements ExporterFactory {

    private ExportRequest request;

    private ExportModel exportModel;

    public JdbcTemplateExporterFactory(ExportRequest request, ExportModel exportModel) {
        this.request = request;
        this.exportModel = exportModel;
    }

    @Override
    public String getExportName() {
        return request.getExport();
    }

    @Override
    public Exporter newInstance() {
        List<FieldSpec> columns = getExportModel().getFieldSpecs().stream()
                .sorted((FieldSpecModel c1, FieldSpecModel c2) -> c1.getIndex().compareTo(c2.getIndex()))
                .map(col -> FieldSpecMapper.create(col))
                .collect(Collectors.toList());
        TableExporter exporter = new TableExporter(getExportName(), columns);
        exporter.setPropertyAccessor(new HashMapPropertyAccessor());
        return exporter;
    }

}
