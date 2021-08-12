package com.angrysurfer.social.shrapnel.services.service;

import com.angrysurfer.social.dto.UserDTO;
import com.angrysurfer.social.shrapnel.component.IExport;
import com.angrysurfer.social.shrapnel.component.property.PropertyMapAccessor;
import com.angrysurfer.social.shrapnel.component.writer.CsvDataWriter;
import com.angrysurfer.social.shrapnel.services.exception.ExportRequestProcessingException;
import com.angrysurfer.social.shrapnel.services.factory.IExportFactory;
import com.angrysurfer.social.shrapnel.services.factory.JdbcTemplateExportFactory;
import com.angrysurfer.social.shrapnel.util.ExcelUtil;
import com.angrysurfer.social.shrapnel.util.FileUtil;
import com.angrysurfer.social.shrapnel.util.PdfUtil;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

public interface IExportsService {

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

    IExportFactory getFactory(Request request);

    default ByteArrayResource exportByteArrayResource(Request request) {
        IExportFactory factory = getFactory(request);
        return Objects.nonNull(factory) ? exportByteArrayResource(request, FileUtil.makeFileName(user, factory)) : null;
    }

    default ByteArrayResource exportByteArrayResource(Request request, String tempFileName) {

        IExportFactory factory = getFactory(request);
        IExport export = newExportInstance(request);
        String filename = null;

        if (Objects.nonNull(export))
            switch (request.getFileType().toLowerCase(Locale.ROOT)) {
                case CSV_FILE:
                    Collection data = factory.getData();
                    CsvDataWriter writer = new CsvDataWriter(export.getFields());
                    if (factory instanceof JdbcTemplateExportFactory)
                        writer.setPropertyAccessor((new PropertyMapAccessor()));
                    filename = FileUtil.makeFileName(user, factory);
                    writer.writeValues(data, filename);
                    break;

                case PDF_FILE:
                    filename = PdfUtil.writeTabularFile(factory.getData(), export.getPdfRowWriter(), tempFileName,
                            export.getPdfPageSize());
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

    default ByteArrayOutputStream exportByteArrayOutputStream(Request request) {

        IExportFactory factory = getFactory(request);
        IExport export = newExportInstance(request);
        if (Objects.nonNull(export))
            switch (request.getFileType().toLowerCase(Locale.ROOT)) {
                case PDF_FILE:
                    return PdfUtil.generateByteArrayOutputStream(factory.getData(), export);

                case XLSX_FILE:
                    return ExcelUtil.generateByteArrayOutputStream(factory.getData(), export);
            }

        return null;
    }

    default IExport newExportInstance(Request request) {
        IExportFactory factory = getFactory(request);
        IExport export = null;
        try {
            export = factory.newInstance();
            if (Objects.nonNull(export))
                return null;

            if (Objects.nonNull(request.getFilterCriteria()) && request.getFilterCriteria().size() > 0)
                export.addFilter(request.getFilterCriteria());

            export.init();

        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                InstantiationException | IllegalAccessException e) {
            throw new ExportRequestProcessingException();
        }
        return export;
    }

}
