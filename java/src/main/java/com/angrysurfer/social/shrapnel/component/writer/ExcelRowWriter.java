package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.component.filter.DataFilterList;
import com.angrysurfer.social.shrapnel.component.format.ValueFormatter;
import com.angrysurfer.social.shrapnel.component.property.Types;
import com.angrysurfer.social.shrapnel.component.style.CombinedStyleProvider;
import com.angrysurfer.social.shrapnel.component.style.ExcelStyleProvider;
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
public class ExcelRowWriter extends AbstractRowWriter implements RowWriter {

    public static final String WORKBOOK = "workbook";

    public static final String SHEET = "sheet";

    private Workbook workbook;

    private Sheet sheet;

    private int[] row = {0};

    private DataFilterList filters = new DataFilterList.DataFilterListImpl();

    private ExcelStyleProvider styleProvider;

    private boolean autoCreateTopLevelHeader = true;

    public ExcelRowWriter(List<ColumnSpec> columns) {
        super(columns);
    }

    public ExcelRowWriter(List<ColumnSpec> columns, ValueFormatter valueFormatter) {
        super(columns, valueFormatter);
    }

    public ExcelRowWriter(List<ColumnSpec> columns, ValueFormatter valueFormatter, CombinedStyleProvider styleProvider) {
        super(columns, valueFormatter);
        setStyleProvider(styleProvider);
    }

    protected void beforeRow(Object item) {
    }

    protected Cell createHeaderCell(ColumnSpec col, Row header, int index) {
        Cell cell = header.createCell(index);
        cell.setCellStyle(getStyleProvider().getHeaderStyle(getWorkbook(), col));
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

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
        onSheetSet(sheet);
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
        onWorkbookSet(workbook);
    }

    protected void writeCell(ColumnSpec col, Object item, Cell cell) {
        if (accessorExists(item, col.getPropertyName()))
            if (getValueFormatter().hasFormatFor(col))
                writeFormattedValue(item, col, cell);
            else
                writeValue(item, col, cell);
    }

    protected void writeDisclaimer() {
        // incrementRow();
    }

    protected void writeDataRow(Object item, Row row) {
        final int[] colNum = {getCellOffSet(item)};
        getColumns().forEach(col -> {
            if (!shouldSkip(col, item) && shouldWrite(col, item)) {
                Cell cell = row.createCell(colNum[0]);
                cell.setCellStyle(getStyleProvider().getCellStyle(item, getWorkbook(), col, getCurrentRow()));
                writeCell(col, item, cell);
                colNum[0]++;
            }
        });
        incrementRow();
    }

    protected void writerHeaderRow() {
        final int[] index = {0};
        Row header = getSheet().createRow(getCurrentRow());
        getColumns().forEach(col -> createHeaderCell(col, header, index[0]++).setCellValue(Objects.nonNull(col.getHeaderLabel()) ?
                col.getHeaderLabel() : col.getPropertyName().toUpperCase(Locale.ROOT)));
        incrementRow();
    }

    @Override
    public void writeValues(Map<String, Object> outputConfig, Collection<Object> items) {
        if (!outputConfig.containsKey(WORKBOOK) || !outputConfig.containsKey(SHEET)
                || !(outputConfig.get(WORKBOOK) instanceof Workbook || !(outputConfig.get(SHEET) instanceof Sheet))
                || Objects.isNull(items))
            return;

        setWorkbook((Workbook) outputConfig.get(WORKBOOK));
        setSheet((Sheet) outputConfig.get(SHEET));
        writeDisclaimer();

        if (autoCreateTopLevelHeader)
            writerHeaderRow();

        items.stream().filter(item -> getFilters().allow(item, this, this)).forEach(item -> {
            beforeRow(item);
            writeDataRow(item, getSheet().createRow(getCurrentRow()));
        });
    }

    protected void writeValue(Object item, ColumnSpec col, Cell cell) {
        switch (col.getType()) {
            case Types.BOOLEAN:
                cell.setCellValue(getBoolean(item, col.getPropertyName()));
                break;
            case Types.DATE:
                cell.setCellValue(getDate(item, col.getPropertyName()));
                break;
            case Types.DOUBLE:
                cell.setCellValue(getDouble(item, col.getPropertyName()));
                break;
            case Types.CALENDAR:
                cell.setCellValue(getCalendar(item, col.getPropertyName()));
                break;
            case Types.LOCALDATE:
                cell.setCellValue(getLocalDate(item, col.getPropertyName()));
                break;
            case Types.LOCALDATETIME:
                cell.setCellValue(getLocalDateTime(item, col.getPropertyName()));
                break;
            case Types.STRING:
                cell.setCellValue(getString(item, col.getPropertyName()));
                break;
            // case Types.RICHTEXT:
            //     cell.setCellValue(getR(item, col.getPropertyName()));
            //     break;
        }
    }

    protected void writeFormattedValue(Object item, ColumnSpec col, Cell cell) {
        switch (col.getType()) {
            case Types.BOOLEAN:
                cell.setCellValue(getValueFormatter().format(col, getBoolean(item, col.getPropertyName())));
                break;
            case Types.DATE:
                cell.setCellValue(getValueFormatter().format(col, getDate(item, col.getPropertyName())));
                break;
            case Types.DOUBLE:
                cell.setCellValue(getValueFormatter().format(col, getDouble(item, col.getPropertyName())));
                break;
            case Types.CALENDAR:
                cell.setCellValue(getValueFormatter().format(col, getCalendar(item, col.getPropertyName())));
                break;
            case Types.LOCALDATE:
                cell.setCellValue(getValueFormatter().format(col, getLocalDate(item, col.getPropertyName())));
                break;
            case Types.LOCALDATETIME:
                cell.setCellValue(getValueFormatter().format(col, getLocalDateTime(item, col.getPropertyName())));
                break;
            case Types.STRING:
                cell.setCellValue(getValueFormatter().format(col, getString(item, col.getPropertyName())));
                break;
            // case Types.RICHTEXT:
            //     cell.setCellValue(getValueFormatter().format(col, getR(item, col.getPropertyName()));
            //     break;
        }
    }

}
