package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.component.filter.DataFilterList;
import com.angrysurfer.social.shrapnel.component.format.ValueFormatter;
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

    private CombinedStyleProvider styleProvider;

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

    protected void writeCell(ColumnSpec col, Object item, Cell cell) {
        if (accessorExists(item, col.getPropertyName()))
            cell.setCellValue(getValueFormatter().hasFormatFor(col) ? getFormattedValue(item, col) : getValue(item, col));
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
        setup(outputConfig, items);
        writeDisclaimer();

        if (autoCreateTopLevelHeader)
            writerHeaderRow();

        items.stream().filter(item -> getFilters().allow(item, this, this)).forEach(item -> {
            beforeRow(item);
            writeDataRow(item, getSheet().createRow(getCurrentRow()));
        });
    }
}
