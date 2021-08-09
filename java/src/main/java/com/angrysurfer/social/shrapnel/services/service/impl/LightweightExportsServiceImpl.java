package com.angrysurfer.social.shrapnel.services.service.impl;

import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.writer.impl.CSVTableWriter;
import com.angrysurfer.social.shrapnel.component.writer.impl.ExcelTableWriter;
import com.angrysurfer.social.shrapnel.component.writer.impl.PdfTableWriter;
import com.angrysurfer.social.shrapnel.services.service.LightweightExportsService;
import com.angrysurfer.social.shrapnel.util.ExcelUtil;
import com.angrysurfer.social.shrapnel.util.FileUtil;
import com.angrysurfer.social.shrapnel.util.PdfUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Slf4j
public class LightweightExportsServiceImpl implements LightweightExportsService {

    @Override
    public String writeCSVFile(Collection<Object> items, List<FieldSpec> columns, String filename) {
        try {
            FileUtil.ensureSafety(filename);
            CSVTableWriter writer = new CSVTableWriter(columns);
            writer.writeValues(items, filename);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return filename;
    }

    @Override
    public String writeExcelFile(Collection<Object> items, List<FieldSpec> columns, String sheetName, String filename) {
        LocalDateTime now = LocalDateTime.now();
        String name = String.format("%s - %s - %s - %s", sheetName, LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        Workbook workbook = new XSSFWorkbook();
        ExcelUtil.addSpreadSheet(workbook, name, items, new ExcelTableWriter(columns));
        try {
            ExcelUtil.writeWorkbookToFile(workbook, filename);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return filename;
    }

    @Override
    public String writeExcelFile(Collection<Object> items, ExcelTableWriter writer, String sheetName, String filename) {
        LocalDateTime now = LocalDateTime.now();
        String name = String.format("%s - %s - %s - %s", sheetName, now.getDayOfMonth(), now.getMonthValue(), now.getYear());
        Workbook workbook = new XSSFWorkbook();
        ExcelUtil.addSpreadSheet(workbook, name, items, writer);
        try {
            ExcelUtil.writeWorkbookToFile(workbook, filename);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return filename;
    }

    @Override
    public String writeTabularPdfFile(Collection<Object> items, List<FieldSpec> columns, String filename) {
        try {
            PdfUtil.writeTabularFile(items, new PdfTableWriter(columns), filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filename;
    }

    @Override
    public String writeTabularPdfFile(Collection<Object> items, PdfTableWriter pdfRowWriter, String filename) {
        try {
            PdfUtil.writeTabularFile(items, pdfRowWriter, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filename;
    }
}
