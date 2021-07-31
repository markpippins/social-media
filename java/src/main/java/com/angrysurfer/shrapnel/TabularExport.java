package com.angrysurfer.shrapnel;

import com.angrysurfer.shrapnel.component.filter.StringStartsWithFilter;
import com.angrysurfer.shrapnel.component.property.ColumnSpec;
import com.angrysurfer.shrapnel.component.writer.ExcelRowWriter;
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

    @Override
    public void addFilter(Map<String, Object> filterCriteria) {
        getExcelRowWriter().getFilters().add(new StringStartsWithFilter(filterCriteria));
    }
}
