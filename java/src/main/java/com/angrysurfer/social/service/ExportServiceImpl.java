package com.angrysurfer.social.service;

import com.angrysurfer.shrapnel.ExportFactory;
import com.angrysurfer.shrapnel.service.ExportRequest;
import com.angrysurfer.shrapnel.service.ExportsRegistryService;
import com.angrysurfer.shrapnel.service.ExportsService;
import com.angrysurfer.shrapnel.util.FileUtil;
import com.angrysurfer.social.dto.UserDTO;
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
    ExportsRegistryService exportRegistry;

    public ExportServiceImpl(List<ExportFactory> exportFactories) {
        this.exportFactories = Objects.nonNull(exportFactories) ? exportFactories : Collections.emptyList();
    }

    @Override
    public ByteArrayResource exportByteArrayResource(ExportRequest request) {
        ExportFactory factory = getFactory(request);
        String filename = FileUtil.makeFileName(user, factory);
        return Objects.nonNull(factory) ? factory.exportByteArrayResource(request, filename) : null;
    }

    @Override
    public ByteArrayOutputStream exportByteArrayOutputStream(ExportRequest request) {
        ExportFactory factory = getFactory(request);
        return Objects.nonNull(factory) ? factory.exportByteArrayOutputStream(request) : null;
    }

    private ExportFactory getFactory(ExportRequest request) {
        return exportFactories.stream().filter(fac -> fac.getExportName().equalsIgnoreCase(request.getExportName())).findFirst()
                .orElseGet(() -> Objects.nonNull(exportRegistry) ? exportRegistry.getFactory(request) : null);
    }
}
