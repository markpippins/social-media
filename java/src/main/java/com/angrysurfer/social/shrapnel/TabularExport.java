package com.angrysurfer.social.shrapnel;

import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.component.filter.StringStartsWithFilter;
import com.angrysurfer.social.shrapnel.component.writer.ExcelRowWriter;
import com.angrysurfer.social.shrapnel.component.writer.PdfRowWriter;
import com.itextpdf.kernel.geom.PageSize;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Getter
@Setter
public class TabularExport implements Export {

    private List<ColumnSpec> columns;

    private String name;

    private ExcelRowWriter excelRowWriter;

    private PdfRowWriter pdfRowWriter;

    public TabularExport(String name, List<ColumnSpec> columns) {
        setName(name);
        setColumns(columns);
    }

    @Override
    public void addFilter(Map<String, Object> filterCriteria) {
        getExcelRowWriter().getFilters().add(new StringStartsWithFilter(filterCriteria));
        getPdfRowWriter().getFilters().add(new StringStartsWithFilter(filterCriteria));
    }

    @Override
    public ExcelRowWriter getExcelRowWriter() {
        if (Objects.isNull(excelRowWriter))
            excelRowWriter = new ExcelRowWriter(getColumns());

        return excelRowWriter;
    }

    @Override
    public PdfRowWriter getPdfRowWriter() {
        if (Objects.isNull(pdfRowWriter))
            pdfRowWriter = new PdfRowWriter(getColumns());

        return pdfRowWriter;
    }

    @Override
    public PageSize getPageSize() {
        return PageSize.Default;
    }
}
