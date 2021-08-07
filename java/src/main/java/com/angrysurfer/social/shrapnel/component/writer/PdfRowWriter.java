package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.component.filter.DataFilterList;
import com.angrysurfer.social.shrapnel.component.format.ValueFormatter;
import com.angrysurfer.social.shrapnel.component.style.CombinedStyleProvider;
import com.angrysurfer.social.shrapnel.component.style.PdfStyleProvider;
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
public class PdfRowWriter extends AbstractRowWriter {

    private static final String DEFAULT_FONT_NAME = "Courier";
    private static final float DEFAULT_FONT_SIZE = 7;

    public static String TABLE = "table";

    private boolean autoCreateTopLevelHeader = true;

    private Table table;

    private CombinedStyleProvider styleProvider;

    private DataFilterList filters = new DataFilterList.DataFilterListImpl();

    public PdfRowWriter(List<ColumnSpec> columns) {
        super(columns);
    }

    public PdfRowWriter(List<ColumnSpec> columns, ValueFormatter valueFormatter) {
        super(columns, valueFormatter);
    }

    protected void beforeRow(Object item) {

    }

    protected Cell createCell(Object item, ColumnSpec col, int row) {
        Cell cell = new Cell();

        if (Objects.nonNull(getStyleProvider().getCellStyle(item, col, row)))
            getStyleProvider().getCellStyle(item, col, row).apply(cell);

        if (Objects.nonNull(col))
            if (ColumnSpec.PADDING_COLUMNS.contains(col))
                cell.add(col.getHeaderLabel());

            else cell.add(getValueFormatter().hasFormatFor(col) ? getFormattedValue(item, col) : getValue(item, col));

        return cell;
    }

    protected Cell createHeaderCell(ColumnSpec col) {
        Cell cell = new Cell();

        if (Objects.nonNull(getStyleProvider().getHeaderStyle(col)))
            getStyleProvider().getHeaderStyle(col).apply(cell);

        return cell.add(col.getHeaderLabel());
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
        float[] widths = new float[getColumnCount()];
        for (int i = 0; i < widths.length - 1; i++)
            widths[i] = 100 / getColumnCount();
        return widths;
    }

    private List<Cell> rightPadDataRow(List<Cell> row, int rowNum) {
        List<Cell> result = new ArrayList<>(row);
        while (result.size() < getColumnCount())
            result.add(createCell(null, ColumnSpec.DATA_PADDING_RIGHT, rowNum));
        return result;
    }

    protected void setup(Map<String, Object> outputConfig, Collection<Object> items) {
        if (!outputConfig.containsKey(TABLE) || !(outputConfig.get(TABLE) instanceof Table || Objects.isNull(items)))
            throw new IllegalArgumentException();

        setTable((Table) outputConfig.get(TABLE));
    }

    public void writeDisclaimer() {

    }

    protected List<Cell> writeDataRow(Object item, int rowNum) {
        List<Cell> row = new ArrayList<>();
        final int[] index = {0};

        getColumns().forEach(col -> {
            if (index[0]++ < getCellOffSet(item))
                row.add(createCell(item, ColumnSpec.DATA_PADDING_LEFT, rowNum));
            else if (shouldWrite(col, item) && !shouldSkip(col, item))
                row.add(createCell(item, accessorExists(item, col.getPropertyName()) ? col : ColumnSpec.DATA_NULL_VALUE, rowNum));
        });

        return rightPadDataRow(row, rowNum);
    }

    public void writeHeaderRow() {
        getColumns().forEach(col -> getTable().addHeaderCell(createHeaderCell(col)));
    }

    @Override
    public void writeValues(Map<String, Object> outputConfig, Collection<Object> items) {
        setup(outputConfig, items);
        writeDisclaimer();
        if (autoCreateTopLevelHeader)
            writeHeaderRow();

        final int[] rowNum = {0};
        items.stream().filter(item -> getFilters().allow(item, this, this)).forEach(item -> {
            beforeRow(item);
            writeDataRow(item, rowNum[0]++).forEach(getTable()::addCell);
        });
    }
}
