package com.angrysurfer.social.shrapnel.util;

import com.angrysurfer.social.shrapnel.Exporter;
import com.angrysurfer.social.shrapnel.component.writer.ExcelRowWriter;
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
        for (int i = 0; i < writer.getFields().size(); i++)
            sheet.setColumnWidth(i, 6000);

        Map<String, Object> config = new HashMap<>();
        config.put(ExcelRowWriter.WORKBOOK, workbook);
        config.put(ExcelRowWriter.SHEET, sheet);

        writer.run(config, items);
    }

    public static ByteArrayOutputStream generateByteArrayOutputStream(Collection<Object> data, Exporter exporter, String tabLabel) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        addSpreadSheet(workbook, tabLabel, data, exporter.getExcelRowWriter());
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

    public static String writeWorkbookToFile(Collection<Object> data, Exporter exporter, String filename) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        addSpreadSheet(workbook, FileUtil.getTabLabel(exporter), data, exporter.getExcelRowWriter());
        return writeWorkbookToFile(workbook, filename);
    }
}
