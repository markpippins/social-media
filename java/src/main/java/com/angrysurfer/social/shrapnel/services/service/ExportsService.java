package com.angrysurfer.social.shrapnel.services.service;

import com.angrysurfer.social.shrapnel.Exporter;
import com.angrysurfer.social.shrapnel.component.property.HashMapPropertyAccessor;
import com.angrysurfer.social.shrapnel.component.writer.CSVRowWriter;
import com.angrysurfer.social.shrapnel.services.ExportRequest;
import com.angrysurfer.social.shrapnel.services.factory.ExporterFactory;
import com.angrysurfer.social.shrapnel.services.factory.impl.JdbcTableExporterFactory;
import com.angrysurfer.social.shrapnel.util.ExcelUtil;
import com.angrysurfer.social.shrapnel.util.FileUtil;
import com.angrysurfer.social.shrapnel.util.PdfUtil;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

public interface ExportsService {

    String CSV_FILE = "csv";
    String PDF_FILE = "pdf";
    String XLSX_FILE = "xlsx";
    long WAIT_SECONDS = 360;

    ExporterFactory getFactory(ExportRequest request);

    default ByteArrayResource exportByteArrayResource(ExportRequest request) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ExporterFactory factory = getFactory(request);
        return Objects.nonNull(factory) ? exportByteArrayResource(request, FileUtil.makeFileName(request.getUser(), factory)) : null;
    }

    default ByteArrayResource exportByteArrayResource(ExportRequest request, String tempFileName) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {

        ExporterFactory factory = getFactory(request);
        Exporter exporter = factory.newInstance();
        if (Objects.isNull(exporter))
            return null;

        if (Objects.nonNull(request.getFilterCriteria()) && request.getFilterCriteria().size() > 0)
            exporter.addFilter(request.getFilterCriteria());

        String filename = null;

        switch (request.getFileType().toLowerCase(Locale.ROOT)) {
            case CSV_FILE:
                Collection data = factory.getData();
                CSVRowWriter writer = new CSVRowWriter(exporter.getFields());
                if (factory instanceof JdbcTableExporterFactory)
                    writer.setPropertyAccessor((new HashMapPropertyAccessor()));
                filename = FileUtil.makeFileName(request.getUser(), factory);
                writer.writeValues(data, filename);
                break;

            case PDF_FILE:
                filename = PdfUtil.writeTabularFile(factory.getData(), exporter.getPdfRowWriter(), tempFileName);
                break;

            case XLSX_FILE:
                filename = ExcelUtil.writeWorkbookToFile(factory.getData(), exporter, tempFileName);
                break;
        }

        if (Objects.nonNull(filename)) {
            FileUtil.removeFileAfter(filename, WAIT_SECONDS);
            return FileUtil.getByteArrayResource(filename);
        }

        return null;
    }

    default ByteArrayOutputStream exportByteArrayOutputStream(ExportRequest request) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ExporterFactory factory = getFactory(request);
        Exporter exporter = factory.newInstance();
        if (Objects.isNull(exporter))
            return null;

        if (Objects.nonNull(request.getFilterCriteria()) && request.getFilterCriteria().size() > 0)
            exporter.addFilter(request.getFilterCriteria());

        switch (request.getFileType().toLowerCase(Locale.ROOT)) {
            case PDF_FILE:
                return PdfUtil.generateByteArrayOutputStream(factory.getData(), exporter);

            case XLSX_FILE:
                return ExcelUtil.generateByteArrayOutputStream(factory.getData(), exporter, FileUtil.getTabLabel(exporter));
        }

        return null;
    }

}
