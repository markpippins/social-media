package com.angrysurfer.social.shrapnel.export;

import com.angrysurfer.social.shrapnel.export.component.field.IField;
import com.angrysurfer.social.shrapnel.export.component.writer.ExcelDataWriter;
import com.angrysurfer.social.shrapnel.export.component.writer.PdfDataWriter;
import com.angrysurfer.social.shrapnel.export.component.writer.filter.IDataFilter;
import com.itextpdf.kernel.geom.PageSize;

import java.util.List;
import java.util.Map;

public interface IExport {

    void addFilter(Map<String, Object> filterCriteria);

    void addFilter(IDataFilter filter);

    List<IField> getFields();

    String getName();

    ExcelDataWriter getExcelRowWriter();

    PdfDataWriter getPdfRowWriter();

    PageSize getPdfPageSize();

    void init();
}
