package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.format.ValueFormatter;
import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.itextpdf.layout.element.Cell;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
public abstract class MultiLevelPdfRowWriter extends PdfRowWriter implements MultiLevelRowWriter {

    protected int level = -1;

    private String levelPropertyName;

    public MultiLevelPdfRowWriter(String levelPropertyName, List<ColumnSpec> columns, ValueFormatter valueFormatter) {
        super(columns, valueFormatter);
        setAutoCreateTopLevelHeader(false);
        setLevelPropertyName(levelPropertyName);
    }

    @Override
    protected void beforeRow(Object item) {
        beforeRow(this, item, getPropertyAccessor());
    }

    @Override
    public int getCellOffSet(Object item) {
        return getCellOffset(this, item, getPropertyAccessor());
    }

    @Override
    public boolean shouldSkip(ColumnSpec col, Object item) {
        return super.shouldSkip(col, item) || shouldSkip(this, col, item, getPropertyAccessor());
    }

    @Override
    public boolean shouldWrite(ColumnSpec col, Object item) {
        return super.shouldWrite(col, item) && shouldWrite(this, col, item, getPropertyAccessor());
    }

    @Override
    public void writeHeader() {
        List<Cell> row = new ArrayList<>();
        leftPadHeaderRow(getLevel() - 1, row);
        getColumnsForLevel(getLevel()).forEach(col -> row.add(createHeaderCell(col)));
        rightPadHeaderRow(row);
        row.forEach(getTable()::addCell);
    }

    protected void leftPadHeaderRow(int startCol, List<Cell> row) {
        while (row.size() < startCol)
            row.add(createHeaderCell(ColumnSpec.HEADER_PADDING_LEFT));
    }

    protected void rightPadHeaderRow(List<Cell> row) {
        while (row.size() < getColumnCount())
            row.add(createHeaderCell(ColumnSpec.HEADER_PADDING_LEFT));
    }
}
