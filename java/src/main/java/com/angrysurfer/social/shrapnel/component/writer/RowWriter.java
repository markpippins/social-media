package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.IValueCalculator;
import com.angrysurfer.social.shrapnel.component.IValueRenderer;
import com.angrysurfer.social.shrapnel.component.field.FieldSpec;
import com.angrysurfer.social.shrapnel.component.field.FieldTypeEnum;
import com.angrysurfer.social.shrapnel.component.field.IFieldSpec;
import com.angrysurfer.social.shrapnel.component.property.IPropertyAccessor;
import com.angrysurfer.social.shrapnel.component.property.IProxyPropertyAccessor;
import com.angrysurfer.social.shrapnel.component.property.PropertyUtilsPropertyAccessor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Getter
@Setter
public abstract class RowWriter implements IRowWriter, IProxyPropertyAccessor {

    public static final boolean DEBUG = false;

    public static final String EMPTY_STRING = "";
    public static final String EMPTY_QUOTES = "''";

    public static final IFieldSpec DATA_NULL_VALUE = new FieldSpec.DebugFieldSpec("dataNullValue", "<< NULL >>", FieldTypeEnum.STRING);
    public static final IFieldSpec DATA_PADDING_LEFT = new FieldSpec.DebugFieldSpec("dataPaddingLeft", "<< DATA <<", FieldTypeEnum.STRING);
    public static final IFieldSpec DATA_PADDING_RIGHT = new FieldSpec.DebugFieldSpec("dataPaddingRight", ">> DATA >>", FieldTypeEnum.STRING);
    public static final IFieldSpec HEADER_PADDING_LEFT = new FieldSpec.DebugFieldSpec("hdrPaddingLeft", "<< HDR <<", FieldTypeEnum.STRING);
    public static final IFieldSpec HEADER_PADDING_RIGHT = new FieldSpec.DebugFieldSpec("hdrPaddingRight", ">> HDR >>", FieldTypeEnum.STRING);

    public static List<IFieldSpec> PADDING_COLUMNS = Arrays.asList(DATA_NULL_VALUE, DATA_PADDING_LEFT, DATA_PADDING_RIGHT,
            HEADER_PADDING_LEFT, HEADER_PADDING_RIGHT);

    private IPropertyAccessor propertyAccessor;

    private List<IFieldSpec> fields;

    private IValueRenderer valueRenderer;

    private IValueCalculator valueCalculator;

    public RowWriter(List<IFieldSpec> fields) {
        setPropertyAccessor(new PropertyUtilsPropertyAccessor());
        setFields(fields);
    }

    public RowWriter(List<IFieldSpec> fields, IValueRenderer valueRenderer) {
        setPropertyAccessor(new PropertyUtilsPropertyAccessor());
        setFields(fields);
        setValueRenderer(valueRenderer);
    }

    public int getCellOffSet(Object item) {
        return 0;
    }

    public IValueCalculator getValueCalculator() {
        if (Objects.isNull(valueCalculator))
            valueCalculator = (field, item) -> item;

        return valueCalculator;
    }

    public IValueRenderer getValueRenderer() {
        if (Objects.isNull(valueRenderer))
            valueRenderer = new IValueRenderer() {

                @Override
                public boolean canRender(IFieldSpec field) {
                    return false;
                }

                @Override
                public String renderCalculatedValue(IFieldSpec field, Object value) {
                    return EMPTY_STRING;
                }
            };

        return valueRenderer;
    }

    public String getValue(Object item, IFieldSpec field) {
        if (accessorExists(item, field.getPropertyName()) || field.getCalculated())
            try {
                switch (field.getType()) {
                    case BOOLEAN:
                        Boolean bool = getBoolean(item, field.getPropertyName());
                        return shouldOnlyRender(field) ?
                                getValueRenderer().render(field, bool) :
                                extendedGetValue(item, field, bool);

                    case CALENDAR:
                        Calendar calendar = getCalendar(item, field.getPropertyName());
                        return shouldOnlyRender(field) ?
                                getValueRenderer().render(field, calendar) :
                                extendedGetValue(item, field, calendar);

                    case DATE:
                        Date date = getDate(item, field.getPropertyName());
                        return shouldOnlyRender(field) ?
                                getValueRenderer().render(field, date) :
                                extendedGetValue(item, field, date);

                    case DOUBLE:
                        Double dbl = getDouble(item, field.getPropertyName());
                        return shouldOnlyRender(field) ?
                                getValueRenderer().render(field, dbl) :
                                extendedGetValue(item, field, dbl);

                    case LOCALDATE:
                        LocalDate localDate = getLocalDate(item, field.getPropertyName());
                        return shouldOnlyRender(field) ?
                                getValueRenderer().render(field, localDate) :
                                extendedGetValue(item, field, localDate);

                    case LOCALDATETIME:
                        LocalDateTime localDateTime = getLocalDateTime(item, field.getPropertyName());
                        return shouldOnlyRender(field) ?
                                getValueRenderer().render(field, localDateTime) :
                                extendedGetValue(item, field, localDateTime);

                    case RICHTEXT:
                        break;

                    case STRING:
                        String string = getString(item, field.getPropertyName());
                        return shouldOnlyRender(field) ?
                                getValueRenderer().render(field, string) :
                                extendedGetValue(item, field, string);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        return EMPTY_STRING;
    }

    private String extendedGetValue(Object item, IFieldSpec field, Object value) {
        return shouldOnlyCalculate(field) ?
                safeString(getValueCalculator().calculateValue(field, item)) :
                shouldCalculateAndRender(field) ? getValueRenderer()
                        .renderCalculatedValue(field, safeString(getValueCalculator().calculateValue(field, item))) :
                        Objects.nonNull(value) ? value.toString() : EMPTY_STRING;
    }

    protected String safeString(Object value) {
        return Objects.nonNull(value) ? value.toString() : EMPTY_STRING;
    }

    protected boolean shouldCalculateAndRender(IFieldSpec field) {
        return getValueRenderer().canRender(field) && field.getCalculated();
    }

    protected boolean shouldOnlyCalculate(IFieldSpec field) {
        return field.getCalculated() && !getValueRenderer().canRender(field);
    }

    protected boolean shouldOnlyRender(IFieldSpec field) {
        return !field.getCalculated() && getValueRenderer().canRender(field);
    }

    public boolean shouldSkip(IFieldSpec field, Object item) {
        return Objects.isNull(field.getPropertyName());
    }

    public boolean shouldWrite(IFieldSpec field, Object item) {
        return Objects.nonNull(field.getPropertyName());
    }
}
