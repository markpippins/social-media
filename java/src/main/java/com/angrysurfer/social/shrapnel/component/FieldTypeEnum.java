package com.angrysurfer.social.shrapnel.component;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum FieldTypeEnum {

    BOOLEAN(1),
    DATE(2),
    DOUBLE(3),
    CALENDAR(4),
    LOCALDATE(5),
    LOCALDATETIME(6),
    RICHTEXT(7),
    STRING(8)

    ;

    private int code;

    FieldTypeEnum(int code) {
        this.code = code;
    }

    public static FieldTypeEnum from(String name) throws IllegalArgumentException {
        FieldTypeEnum result = Arrays.asList(values()).stream().filter(type -> type.name().equals(name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "\nUnknown name '" + name + "', Allowed message properties are "
                                + String.join(", ", Arrays.asList(values()).stream().map((FieldTypeEnum type) -> {
                            return String.join(name, "'", "'");
                        }).collect(Collectors.toList()))));

        return result;
    }

    public int getCode() {
        return code;
    }
};


//    public static final String BOOLEAN = "boolean";
//    public static final String DATE = "date";
//    public static final String DOUBLE = "double";
//    public static final String CALENDAR = "calendar";
//    public static final String LOCALDATE = "localdate";
//    public static final String LOCALDATETIME = "localdatetime";
//    public static final String RICHTEXT = "richtext";
//    public static final String STRING = "string";

