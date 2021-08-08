package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.writer.filter.DataFilterList;
import com.angrysurfer.social.shrapnel.component.writer.style.CombinedStyleProvider;
import com.angrysurfer.social.shrapnel.component.writer.style.PdfStyleProvider;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Getter
@Setter
public class PdfRowWriter extends RowWriter {

    public static String TABLE = "table";

    private boolean autoCreateTopLevelHeader = true;

    private Table table;

    private CombinedStyleProvider styleProvider;

    private DataFilterList filters = new DataFilterList.DataFilterListImpl();

    public PdfRowWriter(List<FieldSpec> fields) {
        super(fields);
    }

    public PdfRowWriter(List<FieldSpec> fields, ValueRenderer valueRenderer) {
        super(fields, valueRenderer);
    }

    protected void beforeRow(Object item) {

    }

    protected Cell createCell(Object item, FieldSpec field, int row) {
        Cell cell = new Cell();

        if (Objects.nonNull(getStyleProvider().getCellStyle(item, field, row)))
            cell.addStyle(getStyleProvider().getCellStyle(item, field, row));

        if (Objects.nonNull(field))
            if (PADDING_COLUMNS.contains(field))
                cell.add(field.getLabel());
            else cell.add(getValue(item, field));

        return cell;
    }

    protected Cell createHeaderCell(FieldSpec field) {
        Cell cell = new Cell();

        if (Objects.nonNull(getStyleProvider().getHeaderStyle(field)))
            cell.addStyle(getStyleProvider().getHeaderStyle(field));

        return cell.add(field.getLabel());
    }

    public Table createTable() {
        return new Table(UnitValue.createPointArray(getTableWidths()));
    }

    public PdfStyleProvider getStyleProvider() {
        if (Objects.isNull(styleProvider))
            styleProvider = new CombinedStyleProvider();

        return styleProvider;
    }

    private float[] getTableWidths() {
        float[] widths = new float[getFieldCount()];
        for (int i = 0; i < widths.length - 1; i++)
            widths[i] = 100 / getFieldCount();
        return widths;
    }

    private List<Cell> rightPadDataRow(List<Cell> row, int rowNum) {
        List<Cell> result = new ArrayList<>(row);
        while (result.size() < getFieldCount())
            result.add(createCell(null, DATA_PADDING_RIGHT, rowNum));
        return result;
    }

    protected void setup(Map<String, Object> outputConfig, Collection<Object> items) {
        if (!outputConfig.containsKey(TABLE) || !(outputConfig.get(TABLE) instanceof Table || Objects.isNull(items)))
            throw new IllegalArgumentException();
        setTable((Table) outputConfig.get(TABLE));
    }

    @Override
    public void writeData(Map<String, Object> outputConfig, Collection<Object> items) {
        setup(outputConfig, items);
        writeDisclaimer();
        if (autoCreateTopLevelHeader)
            writeHeaderRow();

        final int[] rowNum = {0};
        items.stream().filter(item -> getFilters().allow(item, this, this))
                .forEach(item -> {
                    beforeRow(item);
                    writeDataRow(item, rowNum[0]++).forEach(getTable()::addCell);
                });
    }

    @Override
    public void writeError(Exception e) {

    }

    protected List<Cell> writeDataRow(Object item, int rowNum) {
        List<Cell> row = new ArrayList<>();
        final int[] index = {0};

        getFields().forEach(field -> {
            if (index[0]++ < getCellOffSet(item))
                row.add(createCell(item, DATA_PADDING_LEFT, rowNum));
            else if (shouldWrite(field, item) && !shouldSkip(field, item))
                row.add(createCell(item, accessorExists(item, field.getPropertyName()) || field.isCalculated() ? field : DATA_NULL_VALUE, rowNum));
        });

        return rightPadDataRow(row, rowNum);
    }

    public void writeDisclaimer() {

    }

    public void writeHeaderRow() {
        getFields().forEach(field -> getTable().addHeaderCell(createHeaderCell(field)));
    }

}
