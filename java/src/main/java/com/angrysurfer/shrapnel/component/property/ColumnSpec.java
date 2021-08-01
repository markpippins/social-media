package com.angrysurfer.shrapnel.component.property;

import com.angrysurfer.shrapnel.component.writer.RowWriter;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Getter
@Setter
public class ColumnSpec {
    public static final ColumnSpec DATA_NULL_VALUE = new DebugColumnSpec("dataNullValue", "<< NULL >>", Types.STRING);
    public static final ColumnSpec DATA_PADDING_LEFT = new DebugColumnSpec("dataPaddingLeft", "<< DATA <<", Types.STRING);
    public static final ColumnSpec DATA_PADDING_RIGHT = new DebugColumnSpec("dataPaddingRight", ">> DATA >>", Types.STRING);
    public static final ColumnSpec HEADER_PADDING_LEFT = new DebugColumnSpec("hdrPaddingLeft", "<< HDR <<", Types.STRING);
    public static final ColumnSpec HEADER_PADDING_RIGHT = new DebugColumnSpec("hdrPaddingRight", ">> HDR >>", Types.STRING);

    public static List<ColumnSpec> PADDING_COLUMNS = Arrays.asList(DATA_NULL_VALUE, DATA_PADDING_LEFT, DATA_PADDING_RIGHT, HEADER_PADDING_LEFT, HEADER_PADDING_RIGHT);

    private String propertyName;
    private String headerLabel;
    private String type;

    public ColumnSpec(String propertyName, String headerLabel, String type) {
        setPropertyName(propertyName);
        setHeaderLabel(headerLabel);
        setType(type);
    }

    public static List<ColumnSpec> createColumnSpecs(List<String> properties) {
        return properties.stream().map(property -> new ColumnSpec(property, property.toUpperCase(Locale.ROOT), Types.STRING)).collect(Collectors.toList());
    }

    public ColumnSpec cloneWithNewLabel(String newLabel) {
        return new ColumnSpec(getPropertyName(), newLabel, getType());
    }

    static class DebugColumnSpec extends ColumnSpec {

        public DebugColumnSpec(String propertyName, String headerLabel, String type) {
            super(propertyName, headerLabel, type);
        }

        @Override
        public String getHeaderLabel() {
            return RowWriter.DEBUG ? super.getHeaderLabel() : "";
        }
    }
}
