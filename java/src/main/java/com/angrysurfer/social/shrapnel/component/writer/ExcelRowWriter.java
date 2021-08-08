package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.writer.filter.DataFilterList;
import com.angrysurfer.social.shrapnel.component.writer.style.CombinedStyleProvider;
import com.angrysurfer.social.shrapnel.component.writer.style.ExcelStyleProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.*;

@Slf4j
@Getter
@Setter
public class ExcelRowWriter extends RowWriter implements DataWriter {

    public static final String WORKBOOK = "workbook";

    public static final String SHEET = "sheet";

    private Workbook workbook;

    private Sheet sheet;

    private int[] row = {0};

    private DataFilterList filters = new DataFilterList.DataFilterListImpl();

    private CombinedStyleProvider styleProvider;

    private boolean autoCreateTopLevelHeader = true;

    public ExcelRowWriter(List<FieldSpec> fields) {
        super(fields);
    }

    public ExcelRowWriter(List<FieldSpec> fields, ValueRenderer valueRenderer) {
        super(fields, valueRenderer);
    }

    public ExcelRowWriter(List<FieldSpec> fields, ValueRenderer valueRenderer, CombinedStyleProvider styleProvider) {
        super(fields, valueRenderer);
        setStyleProvider(styleProvider);
    }

    protected void beforeRow(Object item) {
    }

    protected Cell createHeaderCell(FieldSpec field, Row header, int index) {
        Cell cell = header.createCell(index);
        cell.setCellStyle(getStyleProvider().getHeaderStyle(getWorkbook(), field));
        return cell;
    }

    protected int getCurrentRow() {
        return row[0];
    }

    protected Sheet getSheet(Map<String, Object> outputConfig) {
        return (Sheet) outputConfig.get(SHEET);
    }

    protected Workbook getWorkbook(Map<String, Object> outputConfig) {
        return (Workbook) outputConfig.get(WORKBOOK);
    }

    protected void incrementRow() {
        row[0]++;
    }

    public void onSheetSet(Sheet sheet) {

    }

    public void onWorkbookSet(Workbook workbook) {

    }

    public ExcelStyleProvider getStyleProvider() {
        if (Objects.isNull(styleProvider))
            styleProvider = new CombinedStyleProvider();

        return styleProvider;
    }

    protected void setup(Map<String, Object> outputConfig, Collection<Object> items) {
        if (!outputConfig.containsKey(WORKBOOK) || !outputConfig.containsKey(SHEET)
                || !(outputConfig.get(WORKBOOK) instanceof Workbook || !(outputConfig.get(SHEET) instanceof Sheet))
                || Objects.isNull(items))
            throw new IllegalArgumentException();

        setWorkbook((Workbook) outputConfig.get(WORKBOOK));
        setSheet((Sheet) outputConfig.get(SHEET));
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
        onSheetSet(sheet);
        getStyleProvider().onSheetSet(sheet);
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
        onWorkbookSet(workbook);
        getStyleProvider().onWorkbookSet(workbook);
    }

    protected void writeCell(FieldSpec field, Object item, Cell cell) {
        if (accessorExists(item, field.getPropertyName()) || field.isCalculated())
            cell.setCellValue(getValue(item, field));
    }

    @Override
    public void writeData(Map<String, Object> outputConfig, Collection<Object> items) {
        setup(outputConfig, items);
        writeDisclaimer();

        if (autoCreateTopLevelHeader)
            writerHeaderRow();

        items.stream().filter(item -> getFilters().allow(item, this, getPropertyAccessor()))
                .forEach(item -> {
                    beforeRow(item);
                    writeDataRow(item, getSheet().createRow(getCurrentRow()));
                });
    }

    protected void writeDataRow(Object item, Row row) {
        final int[] colNum = {getCellOffSet(item)};
        getFields().forEach(field -> {
            if (!shouldSkip(field, item) && shouldWrite(field, item)) {
                Cell cell = row.createCell(colNum[0]++);
                cell.setCellStyle(getStyleProvider().getCellStyle(item, getWorkbook(), field, getCurrentRow()));
                writeCell(field, item, cell);
            }
        });
        incrementRow();
    }

    protected void writeDisclaimer() {
        // incrementRow();
    }

    protected void writerHeaderRow() {
        final int[] index = {0};
        Row header = getSheet().createRow(getCurrentRow());
        getFields().forEach(field -> createHeaderCell(field, header, index[0]++).setCellValue(Objects.nonNull(field.getLabel()) ?
                field.getLabel() : field.getPropertyName().toUpperCase(Locale.ROOT)));
        incrementRow();
    }

}
