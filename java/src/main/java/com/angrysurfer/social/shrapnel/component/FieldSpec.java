package com.angrysurfer.social.shrapnel.component;

import com.angrysurfer.social.shrapnel.component.property.Types;
import com.angrysurfer.social.shrapnel.component.writer.DataWriter;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Getter
@Setter
public class FieldSpec {
    public static final FieldSpec DATA_NULL_VALUE = new DebugFieldSpec("dataNullValue", "<< NULL >>", Types.STRING);
    public static final FieldSpec DATA_PADDING_LEFT = new DebugFieldSpec("dataPaddingLeft", "<< DATA <<", Types.STRING);
    public static final FieldSpec DATA_PADDING_RIGHT = new DebugFieldSpec("dataPaddingRight", ">> DATA >>", Types.STRING);
    public static final FieldSpec HEADER_PADDING_LEFT = new DebugFieldSpec("hdrPaddingLeft", "<< HDR <<", Types.STRING);
    public static final FieldSpec HEADER_PADDING_RIGHT = new DebugFieldSpec("hdrPaddingRight", ">> HDR >>", Types.STRING);

    public static List<FieldSpec> PADDING_COLUMNS = Arrays.asList(DATA_NULL_VALUE, DATA_PADDING_LEFT, DATA_PADDING_RIGHT, HEADER_PADDING_LEFT, HEADER_PADDING_RIGHT);

    private String propertyName;
    private String headerLabel;
    private String type;
    private int index;

    public FieldSpec() {
    }

    public FieldSpec(String propertyName, String headerLabel, String type) {
        setPropertyName(propertyName);
        setHeaderLabel(headerLabel);
        setType(type);
    }

    public static List<FieldSpec> createColumnSpecs(List<String> properties) {
        return properties.stream().map(property -> new FieldSpec(property, property.toUpperCase(Locale.ROOT), Types.STRING)).collect(Collectors.toList());
    }

    public FieldSpec cloneWithNewLabel(String newLabel) {
        return new FieldSpec(getPropertyName(), newLabel, getType());
    }

    static class DebugFieldSpec extends FieldSpec {

        public DebugFieldSpec(String propertyName, String headerLabel, String type) {
            super(propertyName, headerLabel, type);
        }

        @Override
        public String getHeaderLabel() {
            return DataWriter.DEBUG ? super.getHeaderLabel() : "";
        }
    }
}
