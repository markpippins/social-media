package com.angrysurfer.social.shrapnel.services;

import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.writer.ValueCalculator;
import com.angrysurfer.social.shrapnel.component.writer.filter.StringStartsWithFilter;
import com.angrysurfer.social.shrapnel.component.writer.ValueRenderer;
import com.angrysurfer.social.shrapnel.component.property.PropertyAccessor;
import com.angrysurfer.social.shrapnel.component.writer.style.CombinedStyleProvider;
import com.angrysurfer.social.shrapnel.component.writer.ExcelRowWriter;
import com.angrysurfer.social.shrapnel.component.writer.PdfRowWriter;
import com.itextpdf.kernel.geom.PageSize;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Getter
@Setter
public class TabularExport implements Export {

    private List<FieldSpec> fields;

    private String name;

    private ExcelRowWriter excelRowWriter;

    private PdfRowWriter pdfRowWriter;

    public TabularExport(String name, List<FieldSpec> fields) {
        setName(name);
        setFields(fields);
    }

    @Override
    public void addFilter(Map<String, Object> filterCriteria) {
        getExcelRowWriter().getFilters().add(new StringStartsWithFilter(filterCriteria));
        getPdfRowWriter().getFilters().add(new StringStartsWithFilter(filterCriteria));
    }

    @Override
    public ExcelRowWriter getExcelRowWriter() {
        if (Objects.isNull(excelRowWriter))
            excelRowWriter = new ExcelRowWriter(getFields());

        return excelRowWriter;
    }

    @Override
    public PdfRowWriter getPdfRowWriter() {
        if (Objects.isNull(pdfRowWriter))
            pdfRowWriter = new PdfRowWriter(getFields());

        return pdfRowWriter;
    }

    @Override
    public PageSize getPageSize() {
        return PageSize.Default;
    }

    public void setStyleProvider(CombinedStyleProvider styleProvider) {
        getExcelRowWriter().setStyleProvider(styleProvider);
        getPdfRowWriter().setStyleProvider(styleProvider);
    }

    public void setValueCalculator(ValueCalculator valueCalculator) {
        getExcelRowWriter().setValueCalculator(valueCalculator);
        getPdfRowWriter().setValueCalculator(valueCalculator);
    }

    public void setValueRenderer(ValueRenderer valueRenderer) {
        getExcelRowWriter().setValueRenderer(valueRenderer);
        getPdfRowWriter().setValueRenderer(valueRenderer);
    }

    @Override
    public void setPropertyAccessor(PropertyAccessor propertyAccessor) {
        getExcelRowWriter().setPropertyAccessor(propertyAccessor);
        getPdfRowWriter().setPropertyAccessor(propertyAccessor);
    }
}
