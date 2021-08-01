package com.angrysurfer.shrapnel.util;

import com.angrysurfer.shrapnel.Export;
import com.angrysurfer.shrapnel.component.writer.ExcelRowWriter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtil {

    static final String XLSX = "xlsx";

    public static void addSpreadSheet(Workbook workbook, String sheetName, Collection<Object> items, ExcelRowWriter writer) {
        Sheet sheet = workbook.createSheet(sheetName);
        for (int i = 0; i < writer.getColumns().size(); i++)
            sheet.setColumnWidth(i, 6000);

        Map<String, Object> config = new HashMap<>();
        config.put(ExcelRowWriter.WORKBOOK, workbook);
        config.put(ExcelRowWriter.SHEET, sheet);

        writer.writeValues(config, items);
    }

    public static ByteArrayOutputStream generateByteArrayOutputStream(Collection<Object> data, Export export, String tabLabel) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        addSpreadSheet(workbook, tabLabel, data, export.getExcelRowWriter());
        return generateByteArrayOutputStream(workbook);
    }

    private static ByteArrayOutputStream generateByteArrayOutputStream(Workbook workbook) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();
        return baos;
    }

    public static String writeWorkbookToFile(Workbook workbook, String filename) throws IOException {
        String outputFileName = FileUtil.getOutputFileName(filename, XLSX);
        FileUtil.ensureSafety(outputFileName);

        try (FileOutputStream fos = new FileOutputStream(outputFileName)) {
            workbook.write(fos);
            workbook.close();
            return outputFileName;
        }

    }

    public static String writeWorkbookToFile(Collection<Object> data, Export export, String filename) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        addSpreadSheet(workbook, FileUtil.getTabLabel(export), data, export.getExcelRowWriter());
        return writeWorkbookToFile(workbook, filename);
    }

}
