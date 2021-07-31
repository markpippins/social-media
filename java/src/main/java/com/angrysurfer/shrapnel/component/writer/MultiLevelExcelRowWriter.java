package com.angrysurfer.shrapnel.component.writer;

import com.angrysurfer.shrapnel.component.format.ValueFormatter;
import com.angrysurfer.shrapnel.component.property.ColumnSpec;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

@Getter
@Setter
@Slf4j
public abstract class MultiLevelExcelRowWriter extends ExcelRowWriter implements MultiLevelRowWriter {

    private int level = -1;

    private String levelPropertyName;

    public MultiLevelExcelRowWriter(String levelPropertyName, List<ColumnSpec> columns, ValueFormatter valueFormatter) {
        super(columns, valueFormatter);
        setAutoCreateTopLevelHeader(false);
        setLevelPropertyName(levelPropertyName);
    }

    @Override
    protected void beforeRow(Object item) {
        beforeRow(this, item, this);
    }

    @Override
    public int getCellOffSet(Object item) {
        return getCellOffset(this, item, this);
    }

    @Override
    public boolean shouldSkip(ColumnSpec col, Object item) {
        return super.shouldSkip(col, item) || shouldSkip(this, col, item, this);
    }

    @Override
    public boolean shouldWrite(ColumnSpec col, Object item) {
        return super.shouldWrite(col, item) || shouldSkip(this, col, item, this);
    }

    @Override
    public void writeHeader() {
        Row header = getSheet().createRow(getCurrentRow());
        CellStyle headerStyle = getStyleProvider().getHeaderStyle(getWorkbook());

        for (int i = 0; i < getLevel() - 1; i++) {
            Cell cell = header.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(ColumnSpec.HEADER_PADDING_LEFT.getHeaderLabel());
        }
    }

}
