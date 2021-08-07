package com.angrysurfer.social.shrapnel.services.service;

import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.writer.ExcelRowWriter;
import com.angrysurfer.social.shrapnel.component.writer.PdfRowWriter;

import java.util.Collection;
import java.util.List;

public interface LightweightExportsService {

    String writeCSVFile(Collection<Object> items, List<FieldSpec> columns, String filename);

    String writeExcelFile(Collection<Object> items, List<FieldSpec> columns, String sheetName, String filename);

    String writeExcelFile(Collection<Object> items, ExcelRowWriter writer, String sheetName, String filename);

    String writeTabularPdfFile(Collection<Object> items, List<FieldSpec> columns, String filename);

    String writeTabularPdfFile(Collection<Object> items, PdfRowWriter pdfRowWriter, String filename);
}
