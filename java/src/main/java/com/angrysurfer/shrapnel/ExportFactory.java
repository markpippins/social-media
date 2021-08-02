package com.angrysurfer.shrapnel;

import com.angrysurfer.shrapnel.service.ExportRequest;
import com.angrysurfer.shrapnel.util.ExcelUtil;
import com.angrysurfer.shrapnel.util.FileUtil;
import com.angrysurfer.shrapnel.util.PDFUtil;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

public interface ExportFactory {

    final static String CSV_FILE = "csv";
    final static String PDF_FILE = "pdf";
    final static String XLSX_FILE = "xlsx";

    static final long WAIT_SECONDS = 360;

    Collection<Object> getData();

    String getExportName();

    Class<Export> getExportClass();

    default public ByteArrayOutputStream exportByteArrayOutputStream(ExportRequest request) {

        try {
            Export export = newInstance();
            if (Objects.isNull(export))
                return null;

            if (Objects.nonNull(request.getFilterCriteria()) && request.getFilterCriteria().size() > 0)
                export.addFilter(request.getFilterCriteria());

            switch (request.getFileType().toLowerCase(Locale.ROOT)) {
                case PDF_FILE:
                    return PDFUtil.generateByteArrayOutputStream(getData(), export);

                case XLSX_FILE:
                    return ExcelUtil.generateByteArrayOutputStream(getData(), export, FileUtil.getTabLabel(export));
            }
        } catch (Exception e) {
//            log.error(e.getMessage(), e);
        }

        return null;
    }

    default public ByteArrayResource exportByteArrayResource(ExportRequest request, String tempFileName) {
        try {
            Export export = newInstance();
            if (Objects.isNull(export))
                return null;

            if (Objects.nonNull(request.getFilterCriteria()) && request.getFilterCriteria().size() > 0)
                export.addFilter(request.getFilterCriteria());

            String filename = null;

            switch (request.getFileType().toLowerCase(Locale.ROOT)) {
                case PDF_FILE:
                    filename = PDFUtil.writeTabularFile(getData(), export.getPdfRowWriter(), tempFileName);
                    break;

                case XLSX_FILE:
                    filename = ExcelUtil.writeWorkbookToFile(getData(), export, tempFileName);
                    break;
            }

            if (Objects.nonNull(filename)) {
                FileUtil.removeFileAfter(filename, WAIT_SECONDS);
                return FileUtil.getByteArrayResource(filename);
            }
        } catch (Exception e) {
//            log.error(e.getMessage(), e);
        }

        return null;
    }

    default Export newInstance() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(getExportClass().getCanonicalName());
        Constructor<?> ctor = clazz.getConstructor();
        return (Export) ctor.newInstance(new Object[]{});
    }
}
