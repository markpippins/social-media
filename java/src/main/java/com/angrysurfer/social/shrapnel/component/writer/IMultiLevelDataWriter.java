package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.field.IFieldSpec;
import com.angrysurfer.social.shrapnel.component.property.IPropertyAccessor;

import java.util.List;

public interface IMultiLevelDataWriter {

    List<IFieldSpec> getFieldsForLevel(int level);

    int getLevel();

    void setLevel(int level);

    String getLevelPropertyName();

    void writeHeader();

    default void beforeRow(IMultiLevelDataWriter writer, Object item, IPropertyAccessor propertyAccessor) {
        if (propertyAccessor.accessorExists(item, writer.getLevelPropertyName())) {
            int level = Integer.parseInt(propertyAccessor.getString(item, writer.getLevelPropertyName()));
            if (level == writer.getLevel())
                return;

            writer.setLevel(level);
            writer.writeHeader();
        }
    }

    default int getCellOffset(IMultiLevelDataWriter writer, Object item, IPropertyAccessor propertyAccessor) {
        if (propertyAccessor.accessorExists(item, writer.getLevelPropertyName())) {
            int level = Integer.parseInt(propertyAccessor.getString(item, writer.getLevelPropertyName()));
            return level - 1;
        }

        return 0;
    }

    default boolean shouldSkip(IMultiLevelDataWriter writer, IFieldSpec field, Object item, IPropertyAccessor propertyAccessor) {
        if (propertyAccessor.accessorExists(item, writer.getLevelPropertyName())) {
            int level = Integer.parseInt(propertyAccessor.getString(item, writer.getLevelPropertyName()));
            return !writer.getFieldsForLevel(level).contains(field);
        }

        return false;
    }

    default boolean shouldWrite(IMultiLevelDataWriter writer, IFieldSpec field, Object item, IPropertyAccessor propertyAccessor) {
        if (propertyAccessor.accessorExists(item, writer.getLevelPropertyName())) {
            int level = Integer.parseInt(propertyAccessor.getString(item, writer.getLevelPropertyName()));
            return writer.getFieldsForLevel(level).contains(field);
        }

        return true;
    }


}
