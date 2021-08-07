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

    private IndexedColors excelBackgroundColor = IndexedColors.GREY_40_PERCENT;
    private IndexedColors excelForegroundColor = IndexedColors.WHITE;

    private CombinedStyleProvider darkStyleProvider;

    {
        darkStyleProvider = new CombinedStyleProvider() {
            @Override
            public void onWorkbookSet(Workbook workbook) {
                getCellStyle(workbook).setFillForegroundColor(getExcelBackgroundColor().getIndex());
                getCellStyle(workbook).setFillPattern(FillPatternType.SOLID_FOREGROUND);
            }
        };
    }

//    public ZebraStyleProvider() {
//        getDarkStyleProvider().getDefaultCellStyleAdapter().setBackgroundColor(Color.LIGHT_GRAY);
//    }
//
//    public ZebraStyleProvider(Color backgroundColor) {
//        getDarkStyleProvider().getDefaultCellStyleAdapter().setBackgroundColor(backgroundColor);
//    }

    public ZebraStyleProvider(IndexedColors excelBackgroundColor) {
        setExcelBackgroundColor(excelBackgroundColor);
    }

    public ZebraStyleProvider(Color backgroundColor, IndexedColors excelBackgroundColor) {
        getDarkStyleProvider().getDefaultCellStyleAdapter().setBackgroundColor(backgroundColor);
        setExcelBackgroundColor(excelBackgroundColor);
    }

    @Override
    public StyleAdapter getCellStyle(Object item, ColumnSpec col, int row) {
        return row % 2 == 0 ? getDarkStyleProvider().getCellStyle(item, col, row) :
                super.getCellStyle(item, col, row);
    }

    @Override
    public XSSFCellStyle getCellStyle(Object item, Workbook workbook, ColumnSpec col, int row) {
        return row % 2 == 0 ? getDarkStyleProvider().getCellStyle(item, workbook, col, row) :
                super.getCellStyle(item, workbook, col, row);
    }

    @Override
    public void onWorkbookSet(Workbook workbook) {
        getDarkStyleProvider().onWorkbookSet(workbook);
    }
}
