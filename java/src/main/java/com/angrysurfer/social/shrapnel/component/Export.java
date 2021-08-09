package com.angrysurfer.social.shrapnel.component;

import com.angrysurfer.social.shrapnel.component.property.PropertyAccessor;
import com.angrysurfer.social.shrapnel.component.writer.filter.DataFilter;
import com.angrysurfer.social.shrapnel.component.writer.impl.ExcelRowWriter;
import com.angrysurfer.social.shrapnel.component.writer.impl.PdfRowWriter;
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

    void init();

    void setPropertyAccessor(PropertyAccessor propertyAccessor);
}
