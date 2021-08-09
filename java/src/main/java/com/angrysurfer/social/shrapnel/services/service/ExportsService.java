package com.angrysurfer.social.shrapnel.services.service;

import com.angrysurfer.social.dto.UserDTO;
import com.angrysurfer.social.shrapnel.component.Export;
import com.angrysurfer.social.shrapnel.component.property.PropertyMapAccessor;
import com.angrysurfer.social.shrapnel.component.writer.impl.CSVTableWriter;
import com.angrysurfer.social.shrapnel.services.ExportRequest;
import com.angrysurfer.social.shrapnel.services.factory.ExportFactory;
import com.angrysurfer.social.shrapnel.services.factory.impl.JdbcTemplateExportFactory;
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

    static UserDTO user = new UserDTO() {
        @Override
        public String getAlias() {
            return "system-export";
        }
    };

    ExportFactory getFactory(ExportRequest request);

    default boolean isValid(ExportRequest request) {
        return Objects.nonNull(getFactory(request));
    }

    default ByteArrayResource exportByteArrayResource(ExportRequest request) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ExportFactory factory = getFactory(request);
        return Objects.nonNull(factory) ? exportByteArrayResource(request, FileUtil.makeFileName(user, factory)) : null;
    }

    default ByteArrayResource exportByteArrayResource(ExportRequest request, String tempFileName) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {

        ExportFactory factory = getFactory(request);
        Export export = factory.newInstance();
        if (Objects.isNull(export))
            return null;

        if (Objects.nonNull(request.getFilterCriteria()) && request.getFilterCriteria().size() > 0)
            export.addFilter(request.getFilterCriteria());

        export.init();

        String filename = null;
        switch (request.getFileType().toLowerCase(Locale.ROOT)) {
            case CSV_FILE:
                Collection data = factory.getData();
                CSVTableWriter writer = new CSVTableWriter(export.getFields());
                if (factory instanceof JdbcTemplateExportFactory)
                    writer.setPropertyAccessor((new PropertyMapAccessor()));
                filename = FileUtil.makeFileName(user, factory);
                writer.writeValues(data, filename);
                break;

            case PDF_FILE:
                filename = PdfUtil.writeTabularFile(factory.getData(), export.getPdfRowWriter(), tempFileName);
                break;

            case XLSX_FILE:
                filename = ExcelUtil.writeWorkbookToFile(factory.getData(), export, tempFileName);
                break;
        }

        if (Objects.nonNull(filename)) {
            FileUtil.removeFileAfter(filename, WAIT_SECONDS);
            return FileUtil.getByteArrayResource(filename);
        }

        return null;
    }

    default ByteArrayOutputStream exportByteArrayOutputStream(ExportRequest request) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        ExportFactory factory = getFactory(request);
        Export export = factory.newInstance();
        if (Objects.isNull(export))
            return null;

        if (Objects.nonNull(request.getFilterCriteria()) && request.getFilterCriteria().size() > 0)
            export.addFilter(request.getFilterCriteria());

        export.init();

        switch (request.getFileType().toLowerCase(Locale.ROOT)) {
            case PDF_FILE:
                return PdfUtil.generateByteArrayOutputStream(factory.getData(), export);

            case XLSX_FILE:
                return ExcelUtil.generateByteArrayOutputStream(factory.getData(), export);
        }

        return null;
    }

}
