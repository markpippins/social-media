package com.angrysurfer.social.shrapnel.component.style.preset;

import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.component.style.PDFStyleProvider;
import com.angrysurfer.social.shrapnel.component.style.StyleAdapter;
import com.itextpdf.kernel.color.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZebraStyleProvider extends PDFStyleProvider {

    private PDFStyleProvider darkStyleProvider = new PDFStyleProvider();

    public ZebraStyleProvider(Color backgroundColor) {
        darkStyleProvider.getDefaultCellStyleAdapter().setBackgroundColor(backgroundColor);
    }

    @Override
    public StyleAdapter getCellStyle(Object item, ColumnSpec col, int row) {
        if (row % 2 == 1)
            return darkStyleProvider.getCellStyle(item, col, row);

        return super.getCellStyle(item, col, row);
    }
}
