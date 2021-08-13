package com.angrysurfer.social.shrapnel.component.writer.style.provider;

import com.angrysurfer.social.shrapnel.component.field.IFieldSpec;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public interface IExcelStyleProvider {

    XSSFCellStyle getCellStyle(Object item, Workbook workbook, IFieldSpec field, int row);

    default XSSFCellStyle getCellStyle(Workbook workbook, IFieldSpec field) {
        return getCellStyle(null, workbook, field, 0);
    }

    default XSSFCellStyle getCellStyle(Workbook workbook) {
        return getCellStyle(workbook, null);
    }

    XSSFCellStyle getHeaderStyle(Workbook workbook, IFieldSpec field);

    default XSSFCellStyle getHeaderStyle(Workbook workbook) {
        return getHeaderStyle(workbook);
    }

    default void onWorkbookSet(Workbook workbook) {
    }

    default void onSheetSet(Sheet sheet) {
    }
}
