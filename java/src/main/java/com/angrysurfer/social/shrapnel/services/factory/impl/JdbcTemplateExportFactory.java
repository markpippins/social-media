package com.angrysurfer.social.shrapnel.services.factory.impl;

import com.angrysurfer.social.shrapnel.services.Export;
import com.angrysurfer.social.shrapnel.services.TabularExport;
import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.property.PropertyMapAccessor;
import com.angrysurfer.social.shrapnel.services.ExportRequest;
import com.angrysurfer.social.shrapnel.services.factory.ExportFactory;
import com.angrysurfer.social.shrapnel.services.model.ExportModel;
import com.angrysurfer.social.shrapnel.services.model.FieldSpecModel;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public abstract class JdbcTemplateExportFactory implements ExportFactory {

    private ExportRequest request;

    private ExportModel exportModel;

    public JdbcTemplateExportFactory(ExportRequest request, ExportModel exportModel) {
        this.request = request;
        this.exportModel = exportModel;
    }

    @Override
    public String getExportName() {
        return request.getExport();
    }

    @Override
    public Export newInstance() {
        List<FieldSpec> fields = getExportModel().getFieldSpecs().stream()
                .sorted((FieldSpecModel c1, FieldSpecModel c2) -> c1.getIndex().compareTo(c2.getIndex()))
                .map(field -> field.createFieldSpec())
                .collect(Collectors.toList());
        TabularExport exporter = new TabularExport(getExportName(), fields);
        exporter.setPropertyAccessor(new PropertyMapAccessor());
        return exporter;
    }

}
