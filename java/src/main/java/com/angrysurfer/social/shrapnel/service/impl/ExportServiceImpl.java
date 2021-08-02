package com.angrysurfer.social.shrapnel.service.impl;

import com.angrysurfer.social.dto.UserDTO;
import com.angrysurfer.social.shrapnel.ExportFactory;
import com.angrysurfer.social.shrapnel.service.ExportRequest;
import com.angrysurfer.social.shrapnel.service.ExportsFactoryService;
import com.angrysurfer.social.shrapnel.service.ExportsService;
import com.angrysurfer.social.shrapnel.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ExportServiceImpl implements ExportsService {

    static UserDTO user = new UserDTO() {
        @Override
        public String getAlias() {
            return "system-export";
        }
    };

    private final List<ExportFactory> exportFactories;

    @Resource
    ExportsFactoryService exportsRegistry;

    public ExportServiceImpl(List<ExportFactory> exportFactories) {
        this.exportFactories = Objects.nonNull(exportFactories) ? exportFactories : Collections.emptyList();
    }

    @Override
    public ByteArrayResource exportByteArrayResource(ExportRequest request) {
        ExportFactory factory = getFactory(request);
        return Objects.nonNull(factory) ? factory.exportByteArrayResource(request, FileUtil.makeFileName(user, factory)) : null;
    }

    @Override
    public ByteArrayOutputStream exportByteArrayOutputStream(ExportRequest request) {
        ExportFactory factory = getFactory(request);
        return Objects.nonNull(factory) ? factory.exportByteArrayOutputStream(request) : null;
    }

    private ExportFactory getFactory(ExportRequest request) {
        return exportFactories.stream().filter(fac -> fac.getExportName().equalsIgnoreCase(request.getExportName())).findFirst()
                .orElseGet(() -> Objects.nonNull(exportsRegistry) && exportsRegistry.canExport(request) ? exportsRegistry.getFactory(request) : null);
    }
}
