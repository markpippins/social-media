package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.format.AbstractValueFormatter;
import com.angrysurfer.social.shrapnel.component.format.ValueFormatter;
import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.component.property.PropertyUtilsPropertyAccessor;
import com.angrysurfer.social.shrapnel.component.property.ProxyPropertyAccessorImpl;
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
        setPropertyAccessor(new PropertyUtilsPropertyAccessor());
        setColumns(columns);
    }

    public AbstractRowWriter(List<ColumnSpec> columns, ValueFormatter valueFormatter) {
        setPropertyAccessor(new PropertyUtilsPropertyAccessor());
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

    public boolean accessorExists(Object item, String propertyName) {
        return super.accessorExists(item, propertyName);
    }

}
