package com.angrysurfer.shrapnel.component.writer;

import com.angrysurfer.shrapnel.component.ColumnSpec;
import com.angrysurfer.shrapnel.component.filter.DataFilterList;
import com.angrysurfer.shrapnel.component.format.ValueFormatter;
import com.angrysurfer.shrapnel.component.property.Types;
import com.angrysurfer.shrapnel.component.style.CombinedStyleProvider;
import com.angrysurfer.shrapnel.component.style.PDFStyleProvider;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Getter
@Setter
public class PDFRowWriter extends AbstractRowWriter {

    private static final String DEFAULT_FONT_NAME = "Courier";
    private static final float DEFAULT_FONT_SIZE = 7;

    public static String TABLE = "table";

    private boolean autoCreateTopLevelHeader = true;

    private Table table;

    private PDFStyleProvider styleProvider;

    private DataFilterList filters = new DataFilterList.DataFilterListImpl();

    public PDFRowWriter(List<ColumnSpec> columns) {
        super(columns);
    }

    public PDFRowWriter(List<ColumnSpec> columns, ValueFormatter valueFormatter) {
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

            else if (getValueFormatter().hasFormatFor(col))
                writeFormattedValue(item, col, cell);

            else writeValue(item, col, cell);

        return cell;
    }

    protected Cell createHeaderCell(ColumnSpec col) {
        Cell cell = new Cell();

        if (Objects.nonNull(getStyleProvider().getHeaderStyle(col)))
            getStyleProvider().getHeaderStyle(col).apply(cell);

        return cell.add(col.getHeaderLabel());
    }

    public Table createTable() {

        Table table = new Table(UnitValue.createPointArray(getTableWidths()));

        if (getStyleProvider().getCellStyle().hasProperty(Property.FONT))
            try {
                PdfFont font = getStyleProvider().getCellStyle().getProperty(Property.FONT);
                table.setFont(font);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        else try {
            PdfFont font = PdfFontFactory.createFont(PDFRowWriter.DEFAULT_FONT_NAME);
            table.setFont(font);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return table.setFontSize(PDFRowWriter.DEFAULT_FONT_SIZE);
    }

    public PDFStyleProvider getStyleProvider() {
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


    public void writeDisclaimer() {

    }

    protected List<Cell> writeDataRow(Object item, int rowNum) {
        List<Cell> row = new ArrayList<>();
        final int[] index = {0};

        getColumns().forEach(col -> {
            if (index[0] < getCellOffSet(item))
                row.add(createCell(item, ColumnSpec.DATA_PADDING_LEFT, rowNum));
            else if (shouldWrite(col, item) && !shouldSkip(col, item))
                row.add(createCell(item, accessorExists(item, col.getPropertyName()) ? col : ColumnSpec.DATA_NULL_VALUE, rowNum));
        });

        index[0]++;

        return rightPadDataRow(row, rowNum);
    }

    public void writeHeaderRow() {
        getColumns().forEach(col -> getTable().addHeaderCell(createHeaderCell(col)));
    }

    @Override
    public void writeValues(Map<String, Object> outputConfig, Collection<Object> items) {
        if (!outputConfig.containsKey(TABLE) || !(outputConfig.get(TABLE) instanceof Table || Objects.isNull(items)))
            return;

        setTable((Table) outputConfig.get(TABLE));
        writeDisclaimer();
        if (autoCreateTopLevelHeader)
            writeHeaderRow();

        final int[] rowNum = {0};
        items.stream().filter(item -> getFilters().allow(item, this, this)).forEach(item -> {
            beforeRow(item);
            writeDataRow(item, rowNum[0]).forEach(getTable()::addCell);
        });

        rowNum[0]++;
    }

    protected void writeFormattedValue(Object item, ColumnSpec col, Cell cell) {
        if (accessorExists(item, col.getPropertyName()))
            try {
                switch (col.getType()) {
                    case Types.BOOLEAN:
                        Boolean bool = getBoolean(item, col.getPropertyName());
                        if (Objects.nonNull(bool))
                            cell.add(getValueFormatter().format(col, bool));
                        break;

                    case Types.CALENDAR:
                        Calendar calendar = getCalendar(item, col.getPropertyName());
                        if (Objects.nonNull(calendar))
                            cell.add(getValueFormatter().format(col, calendar));
                        break;

                    case Types.DATE:
                        Date date = getDate(item, col.getPropertyName());
                        if (Objects.nonNull(date))
                            cell.add(getValueFormatter().format(col, date));
                        break;

                    case Types.DOUBLE:
                        Double dbl = getDouble(item, col.getPropertyName());
                        if (Objects.nonNull(dbl))
                            cell.add(getValueFormatter().format(col, dbl));
                        break;

                    case Types.LOCALDATE:
                        LocalDate localDate = getLocalDate(item, col.getPropertyName());
                        if (Objects.nonNull(localDate))
                            cell.add(getValueFormatter().format(col, localDate));
                        break;

                    case Types.LOCALDATETIME:
                        LocalDateTime localDateTime = getLocalDateTime(item, col.getPropertyName());
                        if (Objects.nonNull(localDateTime))
                            cell.add(getValueFormatter().format(col, localDateTime));
                        break;

                    case Types.RICHTEXT:
//                        Boolean value = getBooleanValue(item, col.getPropertyName());
//                        if (Objects.nonNull(value))
//                            cell.add(getValueFormatter().format(col, value.toString());
                        break;

                    case Types.STRING:
                        String value = getString(item, col.getPropertyName());
                        if (Objects.nonNull(value))
                            cell.add(getValueFormatter().format(col, value));
                        break;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
    }

    protected void writeValue(Object item, ColumnSpec col, Cell cell) {
        if (accessorExists(item, col.getPropertyName()))
            try {
                switch (col.getType()) {
                    case Types.BOOLEAN:
                        Boolean bool = getBoolean(item, col.getPropertyName());
                        if (Objects.nonNull(bool))
                            cell.add(bool.toString());
                        break;

                    case Types.CALENDAR:
                        Calendar calendar = getCalendar(item, col.getPropertyName());
                        if (Objects.nonNull(calendar))
                            cell.add(calendar.toString());
                        break;

                    case Types.DATE:
                        Date date = getDate(item, col.getPropertyName());
                        if (Objects.nonNull(date))
                            cell.add(date.toString());
                        break;

                    case Types.DOUBLE:
                        Double dbl = getDouble(item, col.getPropertyName());
                        if (Objects.nonNull(dbl))
                            cell.add(dbl.toString());
                        break;

                    case Types.LOCALDATE:
                        LocalDate localDate = getLocalDate(item, col.getPropertyName());
                        if (Objects.nonNull(localDate))
                            cell.add(localDate.toString());
                        break;

                    case Types.LOCALDATETIME:
                        LocalDateTime localDateTime = getLocalDateTime(item, col.getPropertyName());
                        if (Objects.nonNull(localDateTime))
                            cell.add(localDateTime.toString());
                        break;

                    case Types.RICHTEXT:
//                        Boolean value = getBooleanValue(item, col.getPropertyName());
//                        if (Objects.nonNull(value))
//                            cell.add(value.toString());
                        break;

                    case Types.STRING:
                        String value = getString(item, col.getPropertyName());
                        if (Objects.nonNull(value))
                            cell.add(value.toString());
                        break;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
    }

}
