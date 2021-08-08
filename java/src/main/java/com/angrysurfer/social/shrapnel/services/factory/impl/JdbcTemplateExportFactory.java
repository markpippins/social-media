package com.angrysurfer.social.shrapnel.services.factory.impl;

import com.angrysurfer.social.shrapnel.services.Export;
import com.angrysurfer.social.shrapnel.services.TabularExport;
import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.property.PropertyMapAccessor;
import com.angrysurfer.social.shrapnel.services.ExportRequest;
import com.angrysurfer.social.shrapnel.services.factory.ExportFactory;
import com.angrysurfer.social.shrapnel.services.model.DBExport;
import com.angrysurfer.social.shrapnel.services.model.DBFieldSpec;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public abstract class JdbcTemplateExportFactory implements ExportFactory {

    private ExportRequest request;

    private DBExport exportModel;

    public JdbcTemplateExportFactory(ExportRequest request, DBExport exportModel) {
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
                .sorted((DBFieldSpec c1, DBFieldSpec c2) -> c1.getIndex().compareTo(c2.getIndex()))
                .map(field -> field.createFieldSpec())
                .collect(Collectors.toList());
        TabularExport exporter = new TabularExport(getExportName(), fields);
        exporter.setPropertyAccessor(new PropertyMapAccessor());
        return exporter;
    }

}
