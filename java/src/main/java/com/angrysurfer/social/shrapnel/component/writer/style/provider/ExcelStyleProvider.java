package com.angrysurfer.social.shrapnel.component.writer.style.provider;

import com.angrysurfer.social.shrapnel.component.FieldSpec;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public interface ExcelStyleProvider {

    XSSFCellStyle getCellStyle(Object item, Workbook workbook, FieldSpec col, int row);

    default XSSFCellStyle getCellStyle(Workbook workbook, FieldSpec col) {
        return getCellStyle(null, workbook, col, 0);
    }

    default XSSFCellStyle getCellStyle(Workbook workbook) {
        return getCellStyle(workbook, null);
    }

    XSSFCellStyle getHeaderStyle(Workbook workbook, FieldSpec col);

    default XSSFCellStyle getHeaderStyle(Workbook workbook) {
        return getHeaderStyle(workbook);
    }

    default void onWorkbookSet(Workbook workbook) {};

    default void onSheetSet(Sheet sheet) {} ;
}
