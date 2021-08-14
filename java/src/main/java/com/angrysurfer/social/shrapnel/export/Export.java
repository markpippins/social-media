package com.angrysurfer.social.shrapnel.export;

import com.angrysurfer.social.shrapnel.export.component.IValueCalculator;
import com.angrysurfer.social.shrapnel.export.component.IValueRenderer;
import com.angrysurfer.social.shrapnel.export.component.field.IField;
import com.angrysurfer.social.shrapnel.export.component.property.IPropertyAccessor;
import com.angrysurfer.social.shrapnel.export.component.writer.ExcelDataWriter;
import com.angrysurfer.social.shrapnel.export.component.writer.PdfDataWriter;
import com.angrysurfer.social.shrapnel.export.component.writer.filter.IDataFilter;
import com.angrysurfer.social.shrapnel.export.component.writer.filter.StringFieldFilter;
import com.angrysurfer.social.shrapnel.export.component.writer.style.provider.CombinedStyleProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Getter
@Setter
public abstract class Export extends AbstractExport {

    private ExcelDataWriter excelRowWriter;

    private PdfDataWriter pdfRowWriter;

    public Export(String name, List<IField> fields) {
        setName(name);
        fields.forEach(field -> getFields().add(field));
    }

    @Override
    public void addFilter(Map<String, Object> filterCriteria) {
        getExcelRowWriter().getFilters().add(new StringFieldFilter(filterCriteria));
        getPdfRowWriter().getFilters().add(new StringFieldFilter(filterCriteria));
    }

    @Override
    public void addFilter(IDataFilter filter) {
        getExcelRowWriter().getFilters().add(filter);
        getPdfRowWriter().getFilters().add(filter);
    }

    @Override
    public ExcelDataWriter getExcelRowWriter() {
        if (Objects.isNull(excelRowWriter))
            excelRowWriter = new ExcelDataWriter(getFields());

        return excelRowWriter;
    }

    @Override
    public PdfDataWriter getPdfRowWriter() {
        if (Objects.isNull(pdfRowWriter))
            pdfRowWriter = new PdfDataWriter(getFields());

        return pdfRowWriter;
    }

    public void setPropertyAccessor(IPropertyAccessor propertyAccessor) {
        getExcelRowWriter().setPropertyAccessor(propertyAccessor);
        getPdfRowWriter().setPropertyAccessor(propertyAccessor);
    }

    public void setStyleProvider(CombinedStyleProvider styleProvider) {
        getExcelRowWriter().setStyleProvider(styleProvider);
        getPdfRowWriter().setStyleProvider(styleProvider);
    }

    public void setValueCalculator(IValueCalculator valueCalculator) {
        getExcelRowWriter().setValueCalculator(valueCalculator);
        getPdfRowWriter().setValueCalculator(valueCalculator);
    }

    public void setValueRenderer(IValueRenderer valueRenderer) {
        getExcelRowWriter().setValueRenderer(valueRenderer);
        getPdfRowWriter().setValueRenderer(valueRenderer);
    }
}
