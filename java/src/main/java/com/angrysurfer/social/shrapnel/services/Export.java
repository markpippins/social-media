package com.angrysurfer.social.shrapnel.services;

import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.property.PropertyAccessor;
import com.angrysurfer.social.shrapnel.component.writer.ExcelRowWriter;
import com.angrysurfer.social.shrapnel.component.writer.PdfRowWriter;
import com.angrysurfer.social.shrapnel.component.writer.filter.DataFilter;
import com.itextpdf.kernel.geom.PageSize;

import java.util.List;
import java.util.Map;

public interface Export {

    void addFilter(Map<String, Object> filterCriteria);

    void addFilter(DataFilter filter);

    List<FieldSpec> getFields();

    String getName();

    ExcelRowWriter getExcelRowWriter();

    PdfRowWriter getPdfRowWriter();

    PageSize getPageSize();

    void setPropertyAccessor(PropertyAccessor propertyAccessor);
}
