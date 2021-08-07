package com.angrysurfer.social.shrapnel.component.format;

import com.angrysurfer.social.shrapnel.component.FieldSpec;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public interface ValueFormatter {

    String format(FieldSpec col, Boolean value);
    String format(FieldSpec col, Double value);
    String format(FieldSpec col, Calendar value);
    String format(FieldSpec col, Date value);
    String format(FieldSpec col, LocalDate value);
    String format(FieldSpec col, LocalDateTime value);
//    String format(ColumnSpecModel col, Richtext value);
    String format(FieldSpec col, String value);

    boolean hasFormatFor(FieldSpec col);
}
