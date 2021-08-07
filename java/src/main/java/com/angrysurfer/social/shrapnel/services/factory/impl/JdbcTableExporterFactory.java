package com.angrysurfer.social.shrapnel.services.factory.impl;

import com.angrysurfer.social.shrapnel.Exporter;
import com.angrysurfer.social.shrapnel.TableExporter;
import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.component.property.HashMapPropertyAccessor;
import com.angrysurfer.social.shrapnel.services.ExportRequest;
import com.angrysurfer.social.shrapnel.services.factory.ExporterFactory;
import com.angrysurfer.social.shrapnel.services.mapping.ColumnSpecMapper;
import com.angrysurfer.social.shrapnel.services.model.ExportModel;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public abstract class JdbcTableExporterFactory implements ExporterFactory {

    private ExportRequest request;

    private ExportModel exportModel;

    public JdbcTableExporterFactory(ExportRequest request, ExportModel exportModel) {
        this.request = request;
        this.exportModel = exportModel;
    }

    @Override
    public String getExportName() {
        return request.getExport();
    }

    @Override
    public Exporter newInstance() {
        List<ColumnSpec> columns = getExportModel().getColumnSpecs().stream()
                .map(col -> ColumnSpecMapper.create(col))
                .sorted(new Comparator<ColumnSpec>() {
                    @Override
                    public int compare(ColumnSpec columnSpec1, ColumnSpec columnSpec2) {
                        return Integer.compareUnsigned(columnSpec1.getIndex(), columnSpec2.getIndex());
                    }
                })
                .collect(Collectors.toList());
        TableExporter exporter = new TableExporter(getExportName(), columns);
        exporter.setPropertyAccessor(new HashMapPropertyAccessor());
        return exporter;
    }

}
