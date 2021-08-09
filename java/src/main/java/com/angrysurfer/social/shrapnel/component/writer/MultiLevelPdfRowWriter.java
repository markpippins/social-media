package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.ValueRenderer;
import com.angrysurfer.social.shrapnel.component.writer.impl.PdfRowWriter;
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

    public MultiLevelPdfRowWriter(String levelPropertyName, List<FieldSpec> fields, ValueRenderer valueRenderer) {
        super(fields, valueRenderer);
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
    public boolean shouldSkip(FieldSpec field, Object item) {
        return super.shouldSkip(field, item) || shouldSkip(this, field, item, getPropertyAccessor());
    }

    @Override
    public boolean shouldWrite(FieldSpec field, Object item) {
        return super.shouldWrite(field, item) && shouldWrite(this, field, item, getPropertyAccessor());
    }

    @Override
    public void writeHeader() {
        List<Cell> row = new ArrayList<>();
        leftPadHeaderRow(getLevel() - 1, row);
        getFieldsForLevel(getLevel()).forEach(field -> row.add(createHeaderCell(field)));
        rightPadHeaderRow(row);
        row.forEach(getTable()::addCell);
    }

    protected void leftPadHeaderRow(int startCol, List<Cell> row) {
        while (row.size() < startCol)
            row.add(createHeaderCell(HEADER_PADDING_LEFT));
    }

    protected void rightPadHeaderRow(List<Cell> row) {
        while (row.size() < getFieldCount())
            row.add(createHeaderCell(HEADER_PADDING_LEFT));
    }
}
