package com.angrysurfer.social.shrapnel.services.factory;

import com.angrysurfer.social.shrapnel.component.Export;
import com.angrysurfer.social.shrapnel.component.IExport;
import com.angrysurfer.social.shrapnel.component.field.IFieldSpec;
import com.angrysurfer.social.shrapnel.component.property.PropertyMapAccessor;
import com.angrysurfer.social.shrapnel.services.ExportRequest;
import com.angrysurfer.social.shrapnel.services.model.ComponentCreator;
import com.itextpdf.kernel.geom.PageSize;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class JdbcTemplateExportFactory implements IExportFactory {

    private ExportRequest request;

    private com.angrysurfer.social.shrapnel.services.model.Export export;

    public JdbcTemplateExportFactory(ExportRequest request, com.angrysurfer.social.shrapnel.services.model.Export export) {
        this.request = request;
        this.export = export;
    }

    @Override
    public String getExportName() {
        return request.getName();
    }

    @Override
    public IExport newInstance() {

        List<IFieldSpec> fields = getExport().getFieldSpecs().stream()
                .sorted(Comparator.comparing(IFieldSpec::getIndex))
                .map(field -> ComponentCreator.createFieldSpec(field))
                .collect(Collectors.toList());

        final PageSize pageSize = Objects.nonNull(getExport().getPdfPageSize()) ?
                ComponentCreator.createPageSize(getExport().getPdfPageSize()) :
                PageSize.Default;

        Export exporter = new Export(getExportName(), fields) {
            @Override
            public void init() {
                setPropertyAccessor(new PropertyMapAccessor());
            }

            @Override
            public PageSize getPdfPageSize() {
                return pageSize;
            }
        };

        return exporter;
    }

}
