package com.angrysurfer.social.shrapnel;

import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.component.property.PropertyAccessor;
import com.angrysurfer.social.shrapnel.component.writer.ExcelRowWriter;
import com.angrysurfer.social.shrapnel.component.writer.PdfRowWriter;
import com.itextpdf.kernel.geom.PageSize;

import java.util.List;
import java.util.Map;

public interface Exporter {

    void addFilter(Map<String, Object> filterCriteria);

    List<ColumnSpec> getColumns();

    String getName();

    ExcelRowWriter getExcelRowWriter();

    PdfRowWriter getPdfRowWriter();

    PageSize getPageSize();

    void setPropertyAccessor(PropertyAccessor propertyAccessor);
}
