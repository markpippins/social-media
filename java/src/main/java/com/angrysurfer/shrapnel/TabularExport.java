package com.angrysurfer.shrapnel;

import com.angrysurfer.shrapnel.component.ColumnSpec;
import com.angrysurfer.shrapnel.component.filter.StringStartsWithFilter;
import com.angrysurfer.shrapnel.component.writer.ExcelRowWriter;
import com.angrysurfer.shrapnel.component.writer.PDFRowWriter;
import com.itextpdf.kernel.geom.PageSize;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Setter
public class TabularExport implements Export {

    private List<ColumnSpec> columns;

    private String name;

    private ExcelRowWriter excelRowWriter = new ExcelRowWriter(getColumns());

    private PDFRowWriter pdfRowWriter = new PDFRowWriter(getColumns());

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
    public PageSize getPageSize() {
        return PageSize.Default;
    }
}
