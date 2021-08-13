package com.angrysurfer.social.shrapnel.services.factory;

import com.angrysurfer.social.shrapnel.component.Export;
import com.angrysurfer.social.shrapnel.component.IExport;
import com.angrysurfer.social.shrapnel.component.field.IFieldSpec;
import com.angrysurfer.social.shrapnel.component.property.PropertyMapAccessor;
import com.angrysurfer.social.shrapnel.services.service.Request;
import com.itextpdf.kernel.geom.PageSize;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class JdbcTemplateExportFactory implements IExportFactory {

    private Request request;

    private com.angrysurfer.social.shrapnel.services.model.Export export;

    public JdbcTemplateExportFactory(Request request, com.angrysurfer.social.shrapnel.services.model.Export export) {
        this.request = request;
        this.export = export;
    }

    @Override
    public String getExportName() {
        return request.getName();
    }

    @Override
    public IExport newInstance() {
        PageSize pageSize = Objects.nonNull(getExport().getPdfPageSize()) ?
                new PageSize(getExport().getPdfPageSize().getWidth(), getExport().getPdfPageSize().getHeight()) :
                PageSize.Default;

        return new Export(getExportName(), getExport().getFieldSpecs()
                .stream()
                .sorted(Comparator.comparing(IFieldSpec::getIndex))
                .collect(Collectors.toList())) {

            @Override
            public void init() {
                setPropertyAccessor(new PropertyMapAccessor());
            }

            @Override
            public PageSize getPdfPageSize() {
                return pageSize;
            }
        };
    }
}
