package com.angrysurfer.shrapnel.service;

import com.angrysurfer.shrapnel.component.property.ColumnSpec;
import com.angrysurfer.shrapnel.component.writer.ExcelRowWriter;

import java.util.Collection;
import java.util.List;

public interface LightweightExportService {

    void writeCSVFile(Collection<Object> items, List<ColumnSpec> columns, String filename);

    void writeExcelFile(Collection<Object> items, List<ColumnSpec> columns, String sheetName, String filename);

    void writeExcelFile(Collection<Object> items, ExcelRowWriter writer, String sheetName, String filename);

}
