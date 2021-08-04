package com.angrysurfer.social.shrapnel.component.style;

import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.itextpdf.layout.Style;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CombinedStyleProvider extends PdfStyleProvider implements ExcelStyleProvider {

    private static final String DEFAULT_FONT = "Courier";

    protected Map<Style, XSSFCellStyle> styleXSSFCellStyleMap = new HashMap<>();

    private boolean cellDefaultsAccessed = false;
    private boolean headerDefaultsAccessed = false;

    protected void apply(Workbook workbook, StyleAdapter adapter, CellStyle style) {

    }

    private XSSFCellStyle createCellStyle(Workbook workbook) {
        return (XSSFCellStyle) workbook.createCellStyle();
    }

    private XSSFCellStyle createHeaderStyle(Workbook workbook) {
        return (XSSFCellStyle) workbook.createCellStyle();
    }

    @Override
    public XSSFCellStyle getCellStyle(Object item, Workbook workbook, ColumnSpec col, int row) {
        if (Objects.isNull(workbook))
            throw new NullPointerException("CellStyle instance cannot be accessed without a Workbook");

        StyleAdapter adapter = getCellStyle(item, col, row);
        if (!styleXSSFCellStyleMap.containsKey(adapter)) {
            XSSFCellStyle cellStyle = createCellStyle(workbook);
            apply(workbook, adapter, cellStyle);
            setXSSFCellDefaults(workbook, cellStyle);

            if (Objects.nonNull(col) && cellDefaultsAccessed) {
                XSSFCellStyle defaultCellStyle = getCellStyle(workbook);
                cellStyle.cloneStyleFrom(defaultCellStyle);
            }

            styleXSSFCellStyleMap.put(adapter, cellStyle);
        }

        if (Objects.isNull(col))
            cellDefaultsAccessed = true;

        return styleXSSFCellStyleMap.get(adapter);
    }

    @Override
    public XSSFCellStyle getHeaderStyle(Workbook workbook, ColumnSpec col) {
        if (Objects.isNull(workbook))
            throw new NullPointerException("CellStyle instance cannot be accessed without a Workbook");

        StyleAdapter adapter = getHeaderStyle(col);
        if (!styleXSSFCellStyleMap.containsKey(adapter)) {
            XSSFCellStyle headerStyle = createHeaderStyle(workbook);
            apply(workbook, adapter, headerStyle);
            setXSSFHeaderDefaults(workbook, headerStyle);

            if (Objects.nonNull(col) && headerDefaultsAccessed) {
                XSSFCellStyle defaultHeaderStyle = getHeaderStyle(workbook);
                headerStyle.cloneStyleFrom(defaultHeaderStyle);
            }

            styleXSSFCellStyleMap.put(adapter, headerStyle);
        }

        if (Objects.isNull(col))
            headerDefaultsAccessed = true;

        return styleXSSFCellStyleMap.get(adapter);
    }

    private void setXSSFCellDefaults(Workbook workbook, XSSFCellStyle cellStyle) {
        XSSFFont xssfFont = ((XSSFWorkbook) workbook).createFont();
        xssfFont.setFontName("Courier");
        xssfFont.setFontHeight(11);
        xssfFont.setColor(IndexedColors.BLACK.getIndex());
        xssfFont.setBold(false);

        cellStyle.setFont(xssfFont);
    }

    private void setXSSFHeaderDefaults(Workbook workbook, XSSFCellStyle cellStyle) {
        XSSFFont xssfFont = ((XSSFWorkbook) workbook).createFont();
        xssfFont.setFontName("Courier");
        xssfFont.setFontHeight(10.5);
        xssfFont.setColor(IndexedColors.WHITE.getIndex());
        xssfFont.setBold(true);
        cellStyle.setFont(xssfFont);

        XSSFColor color = new XSSFColor(new Color(44, 89, 148));
        cellStyle.setFillForegroundColor(color);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }
}
