package com.angrysurfer.social.shrapnel.util;

import com.angrysurfer.social.shrapnel.component.Export;
import com.angrysurfer.social.shrapnel.component.writer.impl.ExcelTableWriter;
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

    static Sheet createSheet(Workbook workbook, ExcelTableWriter writer, String label) {
        Sheet sheet = workbook.createSheet(label);
        for (int i = 0; i < writer.getFields().size(); i++)
            sheet.setColumnWidth(i, 6000);
        return sheet;
    }

    public static void addSpreadSheet(Workbook workbook, String label, Collection<Object> items, ExcelTableWriter writer) {
        Sheet sheet = createSheet(workbook, writer, label);

        Map<String, Object> config = new HashMap<>();
        config.put(ExcelTableWriter.WORKBOOK, workbook);
        config.put(ExcelTableWriter.SHEET, sheet);

        writer.writeData(config, items);
    }

    public static void addSpreadSheet(Workbook workbook, Export export, Collection<Object> items) {
        addSpreadSheet(workbook, FileUtil.getLabel(export), items, export.getExcelRowWriter());
    }

    public static ByteArrayOutputStream generateByteArrayOutputStream(Collection<Object> data, Export export) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        addSpreadSheet(workbook, export, data);
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
        addSpreadSheet(workbook, export, data);
        return writeWorkbookToFile(workbook, filename);
    }

}
