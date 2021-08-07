package com.angrysurfer.social.shrapnel;

import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.component.writer.ExcelRowWriter;
import com.angrysurfer.social.shrapnel.component.writer.PdfRowWriter;
import com.itextpdf.kernel.geom.PageSize;

import java.util.List;
import java.util.Map;

public interface Exporter {

    void addFilter(Map<String, Object> filterCriteria);

    String getName();

    List<ColumnSpec> getColumns();

    ExcelRowWriter getExcelRowWriter();

    PdfRowWriter getPdfRowWriter();

    PageSize getPageSize();
}
