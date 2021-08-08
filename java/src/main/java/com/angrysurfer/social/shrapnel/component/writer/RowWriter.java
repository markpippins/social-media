package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.FieldTypeEnum;
import com.angrysurfer.social.shrapnel.component.property.PropertyAccessor;
import com.angrysurfer.social.shrapnel.component.property.PropertyUtilsPropertyAccessor;
import com.angrysurfer.social.shrapnel.component.property.ProxyPropertyAccessor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Getter
@Setter
public abstract class RowWriter implements DataWriter, ProxyPropertyAccessor {

    public static final boolean DEBUG = false;

    public static final String EMPTY_STRING = "";

    public static final FieldSpec DATA_NULL_VALUE = new FieldSpec.DebugFieldSpec("dataNullValue", "<< NULL >>", FieldTypeEnum.STRING);
    public static final FieldSpec DATA_PADDING_LEFT = new FieldSpec.DebugFieldSpec("dataPaddingLeft", "<< DATA <<", FieldTypeEnum.STRING);
    public static final FieldSpec DATA_PADDING_RIGHT = new FieldSpec.DebugFieldSpec("dataPaddingRight", ">> DATA >>", FieldTypeEnum.STRING);
    public static final FieldSpec HEADER_PADDING_LEFT = new FieldSpec.DebugFieldSpec("hdrPaddingLeft", "<< HDR <<", FieldTypeEnum.STRING);
    public static final FieldSpec HEADER_PADDING_RIGHT = new FieldSpec.DebugFieldSpec("hdrPaddingRight", ">> HDR >>", FieldTypeEnum.STRING);

    public static List<FieldSpec> PADDING_COLUMNS = Arrays.asList(DATA_NULL_VALUE, DATA_PADDING_LEFT, DATA_PADDING_RIGHT,
            HEADER_PADDING_LEFT, HEADER_PADDING_RIGHT);

    private PropertyAccessor propertyAccessor;

    private List<FieldSpec> fields;

    private ValueRenderer valueRenderer;

    private ValueCalculator valueCalculator;

    public RowWriter(List<FieldSpec> fields) {
        setPropertyAccessor(new PropertyUtilsPropertyAccessor());
        setFields(fields);
    }

    public RowWriter(List<FieldSpec> fields, ValueRenderer valueRenderer) {
        setPropertyAccessor(new PropertyUtilsPropertyAccessor());
        setFields(fields);
        setValueRenderer(valueRenderer);
    }

    public int getCellOffSet(Object item) {
        return 0;
    }

    public ValueCalculator getValueCalculator() {
        if (Objects.isNull(valueCalculator))
            valueCalculator = (field, item) -> item;

        return valueCalculator;
    }

    public ValueRenderer getValueRenderer() {
        if (Objects.isNull(valueRenderer))
            valueRenderer = new ValueRenderer() {

                @Override
                public boolean canRender(FieldSpec field) {
                    return false;
                }

                @Override
                public String renderCalculatedValue(FieldSpec field, Object value) {
                    return EMPTY_STRING;
                }
            };

        return valueRenderer;
    }

    public String getValue(Object item, FieldSpec field) {
        if (accessorExists(item, field.getPropertyName()) || field.isCalculated())
            try {
                switch (field.getType()) {
                    case BOOLEAN:
                        Boolean bool = getBoolean(item, field.getPropertyName());
                        return shouldOnlyFormat(field) ?
                                getValueRenderer().render(field, bool) :
                                extendedGetValue(item, field, bool);

                    case CALENDAR:
                        Calendar calendar = getCalendar(item, field.getPropertyName());
                        return shouldOnlyFormat(field) ?
                                getValueRenderer().render(field, calendar) :
                                extendedGetValue(item, field, calendar);

                    case DATE:
                        Date date = getDate(item, field.getPropertyName());
                        return shouldOnlyFormat(field) ?
                                getValueRenderer().render(field, date) :
                                extendedGetValue(item, field, date);

                    case DOUBLE:
                        Double dbl = getDouble(item, field.getPropertyName());
                        return shouldOnlyFormat(field) ?
                                getValueRenderer().render(field, dbl) :
                                extendedGetValue(item, field, dbl);

                    case LOCALDATE:
                        LocalDate localDate = getLocalDate(item, field.getPropertyName());
                        return shouldOnlyFormat(field) ?
                                getValueRenderer().render(field, localDate) :
                                extendedGetValue(item, field, localDate);

                    case LOCALDATETIME:
                        LocalDateTime localDateTime = getLocalDateTime(item, field.getPropertyName());
                        return shouldOnlyFormat(field) ?
                                getValueRenderer().render(field, localDateTime) :
                                extendedGetValue(item, field, localDateTime);

                    case RICHTEXT:
                        break;

                    case STRING:
                        String string = getString(item, field.getPropertyName());
                        return shouldOnlyFormat(field) ?
                                getValueRenderer().render(field, string) :
                                extendedGetValue(item, field, string);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        return EMPTY_STRING;
    }

    private String extendedGetValue(Object item, FieldSpec field, Object value) {
        return field.isCalculated() && !getValueRenderer().canRender(field) ?
                getValueCalculator().calculateValue(field, item).toString() :
                getValueRenderer().canRender(field) && field.isCalculated() ?
                        getValueRenderer().renderCalculatedValue(field, getValueCalculator().calculateValue(field, item)) :
                        Objects.nonNull(value) ? value.toString() : EMPTY_STRING;
    }

    protected boolean shouldOnlyFormat(FieldSpec field) {
        return !field.isCalculated() && getValueRenderer().canRender(field);
    }

    public boolean shouldSkip(FieldSpec field, Object item) {
        return Objects.isNull(field.getPropertyName());
    }

    public boolean shouldWrite(FieldSpec field, Object item) {
        return Objects.nonNull(field.getPropertyName());
    }
}
