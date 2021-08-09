package com.angrysurfer.social.shrapnel.component;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.angrysurfer.social.shrapnel.component.writer.TableWriter.DEBUG;
import static com.angrysurfer.social.shrapnel.component.writer.TableWriter.EMPTY_STRING;

@Getter
@Setter
public class FieldSpec {

    private String propertyName;
    private String label;
    private int index;
    private FieldTypeEnum type;
    private boolean calculated = false;

    public FieldSpec() {
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

    public enum FieldTypeEnum {

        BOOLEAN(1),
        DATE(2),
        DOUBLE(3),
        CALENDAR(4),
        LOCALDATE(5),
        LOCALDATETIME(6),
        RICHTEXT(7),
        STRING(8);

        private int code;

        FieldTypeEnum(int code) {
            this.code = code;
        }

        public static FieldTypeEnum from(String name) throws IllegalArgumentException {
            FieldTypeEnum result = Arrays.asList(FieldTypeEnum.values()).stream().filter(type -> type.name().equals(name)).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "\nUnknown name '" + name + "', Allowed names are "
                                    + String.join(", ", Arrays.asList(FieldTypeEnum.values()).stream().map((FieldTypeEnum type) -> {
                                return String.join(name, "'", "'");
                            }).collect(Collectors.toList()))));

            return result;
        }

        public int getCode() {
            return code;
        }
    }
}
