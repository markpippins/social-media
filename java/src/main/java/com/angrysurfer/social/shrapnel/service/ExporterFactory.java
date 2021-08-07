package com.angrysurfer.social.shrapnel.service;

import com.angrysurfer.social.shrapnel.Exporter;
import com.angrysurfer.social.shrapnel.service.ExportRequest;
import com.angrysurfer.social.shrapnel.util.ExcelUtil;
import com.angrysurfer.social.shrapnel.util.FileUtil;
import com.angrysurfer.social.shrapnel.util.PdfUtil;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

public interface ExporterFactory {

    final static String CSV_FILE = "csv";
    final static String PDF_FILE = "pdf";
    final static String XLSX_FILE = "xlsx";

    static final long WAIT_SECONDS = 360;

    Collection<Object> getData();

    String getExportName();

    Class<Exporter> getExportClass();

    default public ByteArrayOutputStream exportByteArrayOutputStream(ExportRequest request) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {

        Exporter exporter = newInstance();
        if (Objects.isNull(exporter))
            return null;

        if (Objects.nonNull(request.getFilterCriteria()) && request.getFilterCriteria().size() > 0)
            exporter.addFilter(request.getFilterCriteria());

        switch (request.getFileType().toLowerCase(Locale.ROOT)) {
            case PDF_FILE:
                return PdfUtil.generateByteArrayOutputStream(getData(), exporter);

            case XLSX_FILE:
                return ExcelUtil.generateByteArrayOutputStream(getData(), exporter, FileUtil.getTabLabel(exporter));
        }

        return null;
    }

    default public ByteArrayResource exportByteArrayResource(ExportRequest request, String tempFileName) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {

        Exporter exporter = newInstance();
        if (Objects.isNull(exporter))
            return null;

        if (Objects.nonNull(request.getFilterCriteria()) && request.getFilterCriteria().size() > 0)
            exporter.addFilter(request.getFilterCriteria());

        String filename = null;

        switch (request.getFileType().toLowerCase(Locale.ROOT)) {
            case PDF_FILE:
                filename = PdfUtil.writeTabularFile(getData(), exporter.getPdfRowWriter(), tempFileName);
                break;

            case XLSX_FILE:
                filename = ExcelUtil.writeWorkbookToFile(getData(), exporter, tempFileName);
                break;
        }

        if (Objects.nonNull(filename)) {
            FileUtil.removeFileAfter(filename, WAIT_SECONDS);
            return FileUtil.getByteArrayResource(filename);
        }

        return null;
    }

    default Exporter newInstance() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(getExportClass().getCanonicalName());
        Constructor<?> ctor = clazz.getConstructor();
        return (Exporter) ctor.newInstance(new Object[]{});
    }
}
