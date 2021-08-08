package com.angrysurfer.social.shrapnel.component.writer.style;

import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.writer.style.StyleProvider;
import com.angrysurfer.social.shrapnel.component.writer.style.StyleAdapter;
import com.angrysurfer.social.shrapnel.component.writer.style.preset.adapter.CellStyleAdapter;
import com.angrysurfer.social.shrapnel.component.writer.style.preset.adapter.HeaderCellStyleAdapter;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class PdfStyleProvider implements StyleProvider {

    private boolean applyDefaultStyles = true;

    private StyleAdapter defaultCellStyleAdapter;
    private StyleAdapter defaultHeaderStyleAdapter;

    private Map<FieldSpec, StyleAdapter> cellStyles = new HashMap<>();
    private Map<FieldSpec, StyleAdapter> headerStyles = new HashMap<>();

    @Override
    public StyleAdapter getCellStyle(Object item, FieldSpec col, int row) {
        if (Objects.nonNull(col) && cellStyles.containsKey(col))
            return cellStyles.get(col);

        if (Objects.nonNull(col) && !cellStyles.containsKey(col)) {
            StyleAdapter result = new CellStyleAdapter();

            if (Objects.nonNull(this.defaultCellStyleAdapter))
                result.absorb(this.defaultCellStyleAdapter);

            cellStyles.put(col, result);
            return result;
        }

        if (Objects.isNull(defaultCellStyleAdapter))
            defaultCellStyleAdapter = new CellStyleAdapter();

        return defaultCellStyleAdapter;
    }

    public StyleAdapter getDefaultCellStyleAdapter() {
        if (Objects.isNull(defaultCellStyleAdapter))
            defaultCellStyleAdapter = new CellStyleAdapter();

        return defaultCellStyleAdapter;
    }

    public StyleAdapter getDefaultHeaderStyleAdapter() {
        if (Objects.isNull(defaultHeaderStyleAdapter))
            defaultHeaderStyleAdapter = new CellStyleAdapter();

        return defaultHeaderStyleAdapter;
    }

    @Override
    public StyleAdapter getHeaderStyle(FieldSpec col) {
        if (Objects.nonNull(col) && headerStyles.containsKey(col))
            return headerStyles.get(col);

        if (Objects.nonNull(col) && !headerStyles.containsKey(col)) {
            StyleAdapter result = new HeaderCellStyleAdapter();

            if (Objects.nonNull(this.defaultHeaderStyleAdapter))
                result.absorb(this.defaultHeaderStyleAdapter);

            headerStyles.put(col, result);
            return result;
        }

        if (Objects.isNull(defaultHeaderStyleAdapter))
            defaultHeaderStyleAdapter = new HeaderCellStyleAdapter();

        return defaultHeaderStyleAdapter;
    }
}
