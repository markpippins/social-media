package com.angrysurfer.shrapnel.service.impl;

import com.angrysurfer.shrapnel.Export;
import com.angrysurfer.shrapnel.ExportFactory;
import com.angrysurfer.shrapnel.service.ExportRequest;
import com.angrysurfer.shrapnel.service.ExportsRegistryService;
import com.angrysurfer.shrapnel.service.ExportsService;
import com.angrysurfer.shrapnel.util.ExcelUtil;
import com.angrysurfer.shrapnel.util.FileUtil;
import com.angrysurfer.shrapnel.util.PDFUtil;
import com.angrysurfer.social.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@Slf4j
public class ExportServiceImpl implements ExportsService {

    final static String CSV_FILE = "csv";
    final static String PDF_FILE = "pdf";
    final static String XLSX_FILE = "xlsx";

    private static final long WAIT_SECONDS = 360;

    static UserDTO user = new UserDTO() {
        @Override
        public String getAlias() {
            return "system-export";
        }
    };

    private final List<ExportFactory> exportFactories;

    @Resource
    ExportsRegistryService exportRegistry;

    public ExportServiceImpl(List<ExportFactory> exportFactories) {
        this.exportFactories = Objects.nonNull(exportFactories) ? exportFactories : Collections.emptyList();
    }

    @Override
    public ByteArrayResource exportByteArrayResource(ExportRequest request) {
        try {
            ExportFactory factory = getFactory(request);
            Export export = Objects.nonNull(factory) ? factory.newInstance() : null;

            if (Objects.isNull(export))
                return null;

            if (Objects.nonNull(request.getFilterCriteria()) && request.getFilterCriteria().size() > 0)
                export.addFilter(request.getFilterCriteria());

            String filename = null;

            switch (request.getFileType()) {
                case PDF_FILE:
                    filename = PDFUtil.writeTabularFile(factory.getData(), export.getPdfRowWriter(),
                            FileUtil.makeFileName(user, export));
                    break;

                case XLSX_FILE:
                    filename = ExcelUtil.writeWorkbookToFile(factory.getData(), export,
                            FileUtil.makeFileName(user, export));
                    break;
            }

            if (Objects.nonNull(filename)) {
                FileUtil.removeFileAfter(filename, WAIT_SECONDS);
                return FileUtil.getByteArrayResource(filename);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public ByteArrayOutputStream exportByteArrayOutputStream(ExportRequest request) {

        try {
            ExportFactory factory = getFactory(request);
            Export export = Objects.nonNull(factory) ? factory.newInstance() : null;

            if (Objects.isNull(export))
                return null;

            if (Objects.nonNull(request.getFilterCriteria()) && request.getFilterCriteria().size() > 0)
                export.addFilter(request.getFilterCriteria());

            switch (request.getFileType().toUpperCase(Locale.ROOT)) {
                case PDF_FILE:
                    return PDFUtil.generateByteArrayOutputStream(factory.getData(), export);

                case XLSX_FILE:
                    return ExcelUtil.generateByteArrayOutputStream(factory.getData(), export, FileUtil.getTabLabel(export));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    private ExportFactory getFactory(ExportRequest request) {
        return exportFactories.stream().filter(fac -> fac.getExportName().equalsIgnoreCase(request.getExportName())).findFirst()
                .orElseGet(() -> Objects.nonNull(exportRegistry) ? exportRegistry.getFactory(request) : null);
    }
}
