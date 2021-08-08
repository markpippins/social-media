package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.property.PropertyAccessor;

import java.util.List;

public interface MultiLevelRowWriter {

    List<FieldSpec> getFieldsForLevel(int level);

    int getLevel();

    void setLevel(int level);

    String getLevelPropertyName();

    void writeHeader();

    default void beforeRow(MultiLevelRowWriter writer, Object item, PropertyAccessor propertyAccessor) {
        if (propertyAccessor.accessorExists(item, writer.getLevelPropertyName())) {
            int level = Integer.parseInt(propertyAccessor.getString(item, writer.getLevelPropertyName()));
            if (level == writer.getLevel())
                return;

            writer.setLevel(level);
            writer.writeHeader();
        }
    }

    default int getCellOffset(MultiLevelRowWriter writer, Object item, PropertyAccessor propertyAccessor) {
        if (propertyAccessor.accessorExists(item, writer.getLevelPropertyName())) {
            int level = Integer.parseInt(propertyAccessor.getString(item, writer.getLevelPropertyName()));
            return level - 1;
        }

        return 0;
    }

    default boolean shouldSkip(MultiLevelRowWriter writer, FieldSpec field, Object item, PropertyAccessor propertyAccessor) {
        if (propertyAccessor.accessorExists(item, writer.getLevelPropertyName())) {
            int level = Integer.parseInt(propertyAccessor.getString(item, writer.getLevelPropertyName()));
            return !writer.getFieldsForLevel(level).contains(field);
        }

        return false;
    }

    default boolean shouldWrite(MultiLevelRowWriter writer, FieldSpec field, Object item, PropertyAccessor propertyAccessor) {
        if (propertyAccessor.accessorExists(item, writer.getLevelPropertyName())) {
            int level = Integer.parseInt(propertyAccessor.getString(item, writer.getLevelPropertyName()));
            return writer.getFieldsForLevel(level).contains(field);
        }

        return true;
    }


}
