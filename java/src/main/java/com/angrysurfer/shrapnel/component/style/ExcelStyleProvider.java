package com.angrysurfer.shrapnel.component.style;

import com.angrysurfer.shrapnel.component.ColumnSpec;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public interface ExcelStyleProvider {

    XSSFCellStyle getCellStyle(Object item, Workbook workbook, ColumnSpec col, int row);

    default XSSFCellStyle getCellStyle(Workbook workbook, ColumnSpec col) {
        return getCellStyle(null, workbook, col, 0);
    }

    default XSSFCellStyle getCellStyle(Workbook workbook) {
        return getCellStyle(workbook, null);
    }

    XSSFCellStyle getHeaderStyle(Workbook workbook, ColumnSpec col);

   default XSSFCellStyle getHeaderStyle(Workbook workbook) {
        return getHeaderStyle(workbook);
    }
}
