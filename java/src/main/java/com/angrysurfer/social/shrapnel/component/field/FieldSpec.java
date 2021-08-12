package com.angrysurfer.social.shrapnel.component.field;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.angrysurfer.social.shrapnel.component.writer.DataWriter.DEBUG;
import static com.angrysurfer.social.shrapnel.component.writer.DataWriter.EMPTY_STRING;

@Getter
@Setter
public class FieldSpec implements IFieldSpec {

    private String propertyName;
    private String label;
    private Integer index;
    private FieldTypeEnum type;
    private Boolean calculated = false;

    public FieldSpec() {
    }

    public FieldSpec(String propertyName) {
        this(propertyName, propertyName.toUpperCase(), FieldTypeEnum.STRING);
    }

    public FieldSpec(String propertyName, FieldTypeEnum type) {
        this(propertyName, propertyName.toUpperCase(), type);
    }

    public FieldSpec(String propertyName, String label, FieldTypeEnum type) {
        setPropertyName(propertyName);
        setLabel(label);
        setType(type);
    }

    public static List<FieldSpec> createColumnSpecs(List<String> properties) {
        return properties.stream().map(property -> new FieldSpec(property, property.toUpperCase(Locale.ROOT), FieldTypeEnum.STRING))
                .collect(Collectors.toList());
    }

    public FieldSpec cloneWithNewLabel(String newLabel) {
        return new FieldSpec(getPropertyName(), newLabel, getType());
    }

    public static class DebugFieldSpec extends FieldSpec {

        public DebugFieldSpec(String propertyName, String headerLabel, FieldTypeEnum type) {
            super(propertyName, headerLabel, type);
        }

        @Override
        public String getLabel() {
            return DEBUG ? super.getLabel() : EMPTY_STRING;
        }
    }
}
