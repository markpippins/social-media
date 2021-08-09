package com.angrysurfer.social.shrapnel.component;

import com.angrysurfer.social.shrapnel.component.property.PropertyAccessor;
import com.angrysurfer.social.shrapnel.component.writer.filter.DataFilter;
import com.angrysurfer.social.shrapnel.component.writer.impl.ExcelTableWriter;
import com.angrysurfer.social.shrapnel.component.writer.impl.PdfTableWriter;
import com.itextpdf.kernel.geom.PageSize;

import java.util.List;
import java.util.Map;

public interface Export {

    void addFilter(Map<String, Object> filterCriteria);

    void addFilter(DataFilter filter);

    List<FieldSpec> getFields();

    String getName();

    ExcelTableWriter getExcelRowWriter();

    PdfTableWriter getPdfRowWriter();

    PageSize getPdfPageSize();

    void init();

    void setPropertyAccessor(PropertyAccessor propertyAccessor);
}
