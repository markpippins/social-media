package com.angrysurfer.shrapnel.component.writer;

import com.angrysurfer.shrapnel.component.format.AbstractValueFormatter;
import com.angrysurfer.shrapnel.component.format.ValueFormatter;
import com.angrysurfer.shrapnel.component.property.ColumnSpec;
import com.angrysurfer.shrapnel.component.property.ProxyPropertyAccessorImpl;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public abstract class AbstractRowWriter extends ProxyPropertyAccessorImpl implements RowWriter {
    private List<ColumnSpec> columns;

    private ValueFormatter valueFormatter;

    public AbstractRowWriter(List<ColumnSpec> columns) {
        setColumns(columns);
    }

    public AbstractRowWriter(List<ColumnSpec> columns, ValueFormatter valueFormatter) {
        setColumns(columns);
        setValueFormatter(valueFormatter);
    }

    public ValueFormatter getValueFormatter() {
        if (Objects.isNull(valueFormatter))
            valueFormatter = new AbstractValueFormatter() {

                @Override
                public boolean hasFormatFor(ColumnSpec col) {
                    return false;
                }
            };

        return valueFormatter;
    }

    public int getColumnCount() {
        return getColumns().size();
    }

    public int getCellOffSet(Object item) {
        return 0;
    }

    public boolean shouldSkip(ColumnSpec col, Object item) {
        return Objects.isNull(col.getPropertyName());
    }

    public boolean shouldWrite(ColumnSpec col, Object item) {
        return Objects.nonNull(col.getPropertyName());
    }

    public boolean valueExists(Object item, String propertyName) {
        return super.valueExists(item, propertyName);
    }

    public boolean valueExists(Object item, ColumnSpec col) {
        return valueExists(item, col.getPropertyName());
    }
}