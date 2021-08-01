package com.angrysurfer.shrapnel.component.format;

import com.angrysurfer.shrapnel.component.property.ColumnSpec;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public interface ValueFormatter {

    String format(ColumnSpec col, Boolean value);
    String format(ColumnSpec col, Double value);
    String format(ColumnSpec col, Calendar value);
    String format(ColumnSpec col, Date value);
    String format(ColumnSpec col, LocalDate value);
    String format(ColumnSpec col, LocalDateTime value);
//    String format(ColumnSpecification col, Richtext value);
    String format(ColumnSpec col, String value);

    boolean hasFormatFor(ColumnSpec col);
}
