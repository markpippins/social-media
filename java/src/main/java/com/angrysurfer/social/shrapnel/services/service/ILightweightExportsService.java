package com.angrysurfer.social.shrapnel.services.service;

import com.angrysurfer.social.shrapnel.component.field.IFieldSpec;
import com.angrysurfer.social.shrapnel.component.writer.ExcelDataWriter;
import com.angrysurfer.social.shrapnel.component.writer.PdfDataWriter;

import java.util.Collection;
import java.util.List;

public interface ILightweightExportsService {

    String writeCSVFile(Collection<Object> items, List<IFieldSpec> columns, String filename);

    String writeExcelFile(Collection<Object> items, List<IFieldSpec> columns, String sheetName, String filename);

    String writeExcelFile(Collection<Object> items, ExcelDataWriter writer, String sheetName, String filename);

    String writeTabularPdfFile(Collection<Object> items, List<IFieldSpec> columns, String filename);

    String writeTabularPdfFile(Collection<Object> items, PdfDataWriter pdfRowWriter, String filename);
}
