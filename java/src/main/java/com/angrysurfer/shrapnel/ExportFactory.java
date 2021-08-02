package com.angrysurfer.shrapnel;

import com.angrysurfer.shrapnel.service.ExportRequest;
import com.angrysurfer.shrapnel.util.ExcelUtil;
import com.angrysurfer.shrapnel.util.FileUtil;
import com.angrysurfer.shrapnel.util.PDFUtil;
import com.angrysurfer.social.dto.UserDTO;
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

    default Export newInstance() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(getExportClass().getCanonicalName());
        Constructor<?> ctor = clazz.getConstructor();
        return (Export) ctor.newInstance(new Object[]{});
    }

    default public ByteArrayResource exportByteArrayResource(UserDTO user, ExportRequest request) {
        try {
            Export export = newInstance();
            if (Objects.isNull(export))
                return null;

            if (Objects.nonNull(request.getFilterCriteria()) && request.getFilterCriteria().size() > 0)
                export.addFilter(request.getFilterCriteria());

            String filename = null;

            switch (request.getFileType()) {
                case PDF_FILE:
                    filename = PDFUtil.writeTabularFile(getData(), export.getPdfRowWriter(),
                            FileUtil.makeFileName(user, export));
                    break;

                case XLSX_FILE:
                    filename = ExcelUtil.writeWorkbookToFile(getData(), export,
                            FileUtil.makeFileName(user, export));
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

    default public ByteArrayOutputStream exportByteArrayOutputStream(ExportRequest request) {

        try {
            Export export = newInstance();
            if (Objects.isNull(export))
                return null;

            if (Objects.nonNull(request.getFilterCriteria()) && request.getFilterCriteria().size() > 0)
                export.addFilter(request.getFilterCriteria());

            switch (request.getFileType().toUpperCase(Locale.ROOT)) {
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
}
