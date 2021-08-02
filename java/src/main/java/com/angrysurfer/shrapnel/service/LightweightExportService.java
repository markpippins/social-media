package com.angrysurfer.shrapnel.service;

import com.angrysurfer.shrapnel.component.ColumnSpec;
import com.angrysurfer.shrapnel.component.writer.ExcelRowWriter;
import com.angrysurfer.shrapnel.component.writer.PDFRowWriter;

import java.util.Collection;
import java.util.List;

public interface LightweightExportService {

    String writeCSVFile(Collection<Object> items, List<ColumnSpec> columns, String filename);

    String writeExcelFile(Collection<Object> items, List<ColumnSpec> columns, String sheetName, String filename);

    String writeExcelFile(Collection<Object> items, ExcelRowWriter writer, String sheetName, String filename);

    String writeTabularPdfFile(Collection<Object> items, List<ColumnSpec> columns, String filename);

    String writeTabularPdfFile(Collection<Object> items, PDFRowWriter pdfRowWriter, String filename);
}
