package com.angrysurfer.social.shrapnel.component.style.preset;

import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.component.style.CombinedStyleProvider;
import com.angrysurfer.social.shrapnel.component.style.StyleAdapter;
import com.itextpdf.kernel.color.Color;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

@Getter
@Setter
public class ZebraStyleProvider extends CombinedStyleProvider {

    public ZebraStyleProvider(Color backgroundColor) {
        darkStyleProvider.getDefaultCellStyleAdapter().setBackgroundColor(backgroundColor);
    }

    @Override
    public StyleAdapter getCellStyle(Object item, ColumnSpec col, int row) {
        return row % 2 == 1 ? darkStyleProvider.getCellStyle(item, col, row) :
                super.getCellStyle(item, col, row);
    }

    @Override
    public XSSFCellStyle getCellStyle(Object item, Workbook workbook, ColumnSpec col, int row) {
        return row % 2 == 0 ? darkStyleProvider.getCellStyle(item, workbook, col, row) :
                super.getCellStyle(item, workbook, col, row);
    }

    @Override
    public void onWorkbookSet(Workbook workbook) {
        getDarkStyleProvider().onWorkbookSet(workbook);
    }

    private CombinedStyleProvider darkStyleProvider = new CombinedStyleProvider() {
        @Override
        public void onWorkbookSet(Workbook workbook) {
            getDarkStyleProvider().getCellStyle(workbook).setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
            getDarkStyleProvider().getCellStyle(workbook).setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
    };
}
