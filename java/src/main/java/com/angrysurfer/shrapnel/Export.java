package com.angrysurfer.shrapnel;

import com.angrysurfer.shrapnel.component.property.ColumnSpec;
import com.angrysurfer.shrapnel.component.writer.ExcelRowWriter;

import java.util.List;
import java.util.Map;

public interface Export {

    void addFilter(Map<String, Object> filterCriteria);

    List<ColumnSpec> getColumns();

//    PDFRowWriter getPDFRowWriter();

    ExcelRowWriter getExcelRowWriter();

    String getName();

}
