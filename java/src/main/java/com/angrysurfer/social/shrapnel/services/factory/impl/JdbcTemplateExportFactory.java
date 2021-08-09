package com.angrysurfer.social.shrapnel.services.factory.impl;

import com.angrysurfer.social.shrapnel.component.TabularExport;
import com.angrysurfer.social.shrapnel.component.property.PropertyMapAccessor;
import com.angrysurfer.social.shrapnel.services.ExportRequest;
import com.angrysurfer.social.shrapnel.services.factory.ExportFactory;
import com.angrysurfer.social.shrapnel.services.model.Export;
import com.angrysurfer.social.shrapnel.services.model.FieldSpec;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class JdbcTemplateExportFactory implements ExportFactory {

    private ExportRequest request;

    private Export export;

    public JdbcTemplateExportFactory(ExportRequest request, Export export) {
        this.request = request;
        this.export = export;
    }

    @Override
    public String getExportName() {
        return request.getName();
    }

    @Override
    public com.angrysurfer.social.shrapnel.component.Export newInstance() {

        List<com.angrysurfer.social.shrapnel.component.FieldSpec> fields = getExport().getFieldSpecs().stream()
                .sorted((FieldSpec c1, FieldSpec c2) -> c1.getIndex().compareTo(c2.getIndex()))
                .map(field -> field.createFieldSpec())
                .collect(Collectors.toList());

        TabularExport exporter = new TabularExport(getExportName(), fields) {
            @Override
            public void init() {
                setPropertyAccessor(new PropertyMapAccessor());
            }
        };

        return exporter;
    }

}
