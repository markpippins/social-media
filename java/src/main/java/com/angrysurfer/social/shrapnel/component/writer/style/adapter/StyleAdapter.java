package com.angrysurfer.social.shrapnel.component.writer.style.adapter;

import com.itextpdf.layout.Style;
import com.itextpdf.layout.property.VerticalAlignment;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class StyleAdapter extends Style {

    final int MAX_PROPERTY_INDEX = 250;

    private final Map<Integer, Object> extendedProperties = new HashMap<>();

    public void absorb(Style style) {
        for (int index = 0; index < MAX_PROPERTY_INDEX; index++)
            if (style.hasProperty(index))
                setProperty(index, style.getProperty(index));

        if (style instanceof StyleAdapter) {
            StyleAdapter adapter = (StyleAdapter) style;
            for (int index = 0; index < MAX_PROPERTY_INDEX; index++)
                if (adapter.hasExtendedProperty(index))
                    setExtendedProperty(index, adapter.getExtendedProperty(index));
        }
    }

    public boolean hasExtendedProperty(int property) {
        return extendedProperties.containsKey(property);
    }

    public Object getExtendedProperty(int property) {
        return hasExtendedProperty(property) ? extendedProperties.get(property) : null;
    }

    public void setExtendedProperty(int property, Object value) {
        if (Objects.nonNull(value))
            extendedProperties.put(property, value);
        else if (hasExtendedProperty(property))
            extendedProperties.remove(property);
    }

    // overridden Style methods

    @Override
    public Float getMarginLeft() {
        return super.getMarginLeft();
    }

    @Override
    public StyleAdapter setMarginLeft(float value) {
        super.setMarginLeft(value);
        return this;
    }

    @Override
    public Float getMarginRight() {
        return super.getProperty(45);
    }

    @Override
    public StyleAdapter setMarginRight(float value) {
        super.setMarginRight(value);
        return this;
    }

    @Override
    public Float getMarginTop() {
        return super.getMarginTop();
    }

    @Override
    public StyleAdapter setMarginTop(float value) {
        super.setMarginTop(value);
        return this;
    }

    @Override
    public Float getMarginBottom() {
        return super.getMarginBottom();
    }

    @Override
    public StyleAdapter setMarginBottom(float value) {
        super.setProperty(43, value);
        return this;
    }

    @Override
    public StyleAdapter setMargin(float commonMargin) {
        super.setMargins(commonMargin, commonMargin, commonMargin, commonMargin);
        return this;
    }

    @Override
    public StyleAdapter setMargins(float marginTop, float marginRight, float marginBottom, float marginLeft) {
        super.setMarginTop(marginTop);
        super.setMarginRight(marginRight);
        super.setMarginBottom(marginBottom);
        super.setMarginLeft(marginLeft);
        return this;
    }

    @Override
    public Float getPaddingLeft() {
        return super.getPaddingLeft();
    }

    @Override
    public StyleAdapter setPaddingLeft(float value) {
        super.setPaddingLeft(value);
        return this;
    }

    @Override
    public Float getPaddingRight() {
        return super.getPaddingRight();
    }

    @Override
    public StyleAdapter setPaddingRight(float value) {
        super.setPaddingRight(value);
        return this;
    }

    @Override
    public Float getPaddingTop() {
        return super.getPaddingTop();
    }

    @Override
    public StyleAdapter setPaddingTop(float value) {
        super.setPaddingTop(value);
        return this;
    }

    @Override
    public Float getPaddingBottom() {
        return super.getPaddingBottom();
    }

    @Override
    public StyleAdapter setPaddingBottom(float value) {
        super.setPaddingBottom(value);
        return this;
    }

    @Override
    public StyleAdapter setPadding(float commonPadding) {
        return (StyleAdapter) super.setPaddings(commonPadding, commonPadding, commonPadding, commonPadding);
    }

    @Override
    public StyleAdapter setPaddings(float paddingTop, float paddingRight, float paddingBottom, float paddingLeft) {
        super.setPaddingTop(paddingTop);
        super.setPaddingRight(paddingRight);
        super.setPaddingBottom(paddingBottom);
        super.setPaddingLeft(paddingLeft);
        return this;
    }

    @Override
    public StyleAdapter setVerticalAlignment(VerticalAlignment verticalAlignment) {
        super.setVerticalAlignment(verticalAlignment);
        return this;
    }

    @Override
    public StyleAdapter setSpacingRatio(float ratio) {
        super.setSpacingRatio(ratio);
        return this;
    }

    @Override
    public Boolean isKeepTogether() {
        return super.isKeepTogether();
    }

    @Override
    public StyleAdapter setKeepTogether(boolean keepTogether) {
        super.setKeepTogether(keepTogether);
        return this;
    }

    @Override
    public StyleAdapter setRotationAngle(float radAngle) {
        super.setRotationAngle(radAngle);
        return this;
    }

    @Override
    public StyleAdapter setRotationAngle(double angle) {
        super.setRotationAngle((float) angle);
        return this;
    }
}
