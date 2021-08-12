package com.angrysurfer.social.shrapnel.component;

import com.angrysurfer.social.shrapnel.component.field.IFieldSpec;
import com.angrysurfer.social.shrapnel.component.property.IPropertyAccessor;
import com.angrysurfer.social.shrapnel.component.writer.ExcelRowWriter;
import com.angrysurfer.social.shrapnel.component.writer.PdfRowWriter;
import com.angrysurfer.social.shrapnel.component.writer.filter.IDataFilter;
import com.itextpdf.kernel.geom.PageSize;

import java.util.List;
import java.util.Map;

public interface IExport {

    void addFilter(Map<String, Object> filterCriteria);

    void addFilter(IDataFilter filter);

    List<IFieldSpec> getFields();

    String getName();

    ExcelRowWriter getExcelRowWriter();

    PdfRowWriter getPdfRowWriter();

    PageSize getPdfPageSize();

    void init();

    void setPropertyAccessor(IPropertyAccessor propertyAccessor);
}
