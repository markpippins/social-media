package com.angrysurfer.shrapnel.service.impl;

import com.angrysurfer.shrapnel.component.property.ColumnSpec;
import com.angrysurfer.shrapnel.component.writer.SimpleCSVRowWriter;
import com.angrysurfer.shrapnel.component.writer.ExcelRowWriter;
import com.angrysurfer.shrapnel.service.LightweightExportService;
import com.angrysurfer.shrapnel.util.ExcelUtil;
import com.angrysurfer.shrapnel.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Slf4j
public class LightweightExportServiceImpl implements LightweightExportService {

    @Override
    public void writeCSVFile(Collection<Object> items, List<ColumnSpec> columns, String filename) {
        try {
            FileUtil.ensureSafety(filename);
            SimpleCSVRowWriter writer = new SimpleCSVRowWriter(columns);
            writer.writeValues(items, filename);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void writeExcelFile(Collection<Object> items, List<ColumnSpec> columns, String sheetName, String filename) {
        LocalDateTime now = LocalDateTime.now();
        String name = String.format("%s - %s - %s - %s", sheetName, LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        Workbook workbook = new XSSFWorkbook();
        ExcelUtil.addSpreadSheet(workbook, name, items, new ExcelRowWriter(columns));
        try {
            ExcelUtil.writeWorkbookToFile(workbook, filename);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void writeExcelFile(Collection<Object> items, ExcelRowWriter writer, String sheetName, String filename) {
        LocalDateTime now = LocalDateTime.now();
        String name = String.format("%s - %s - %s - %s", sheetName, LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        Workbook workbook = new XSSFWorkbook();
        ExcelUtil.addSpreadSheet(workbook, name, items, writer);
        try {
            ExcelUtil.writeWorkbookToFile(workbook, filename);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
