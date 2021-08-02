package com.angrysurfer.shrapnel.component.style;

import com.angrysurfer.shrapnel.component.ColumnSpec;
import com.angrysurfer.shrapnel.component.style.preset.CellStyleAdapter;
import com.angrysurfer.shrapnel.component.style.preset.HeaderStyleAdapter;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class PDFStyleProvider implements StyleProvider {

    private boolean applyDefaultStyles = true;

    private StyleAdapter iTextCellStyleDefault;

    private StyleAdapter iTextHeaderStyleDefault;

    private Map<ColumnSpec, StyleAdapter> iTextCellStyles = new HashMap<>();

    private Map<ColumnSpec, StyleAdapter> iTextHeaderStyles = new HashMap<>();

    @Override
    public StyleAdapter getCellStyle(Object item, ColumnSpec col, int row) {
        if (Objects.nonNull(col) && iTextCellStyles.containsKey(col))
            return iTextCellStyles.get(col);

        if (Objects.nonNull(col) && !iTextCellStyles.containsKey(col)) {
            StyleAdapter result = new CellStyleAdapter();
            if (Objects.nonNull(this.iTextCellStyleDefault))
                result.absorb(this.iTextCellStyleDefault);
            iTextCellStyles.put(col, result);
            return result;
        }

        if (Objects.isNull(iTextCellStyleDefault))
            iTextCellStyleDefault = new CellStyleAdapter();
        
        return iTextCellStyleDefault;
    }

    @Override
    public StyleAdapter getHeaderStyle(ColumnSpec col) {
        if (Objects.nonNull(col) && iTextHeaderStyles.containsKey(col))
            return iTextHeaderStyles.get(col);

        if (Objects.nonNull(col) && !iTextHeaderStyles.containsKey(col)) {
            StyleAdapter result = new HeaderStyleAdapter();
            if (Objects.nonNull(this.iTextHeaderStyleDefault))
                result.absorb(this.iTextHeaderStyleDefault);
            iTextHeaderStyles.put(col, result);
            return result;
        }

        if (Objects.isNull(iTextHeaderStyleDefault))
            iTextHeaderStyleDefault = new HeaderStyleAdapter();

        return iTextHeaderStyleDefault;
    }
}
