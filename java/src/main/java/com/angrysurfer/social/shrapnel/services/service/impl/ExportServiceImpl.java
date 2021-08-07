package com.angrysurfer.social.shrapnel.services.service.impl;

import com.angrysurfer.social.shrapnel.services.ExportRequest;
import com.angrysurfer.social.shrapnel.services.factory.ExporterFactory;
import com.angrysurfer.social.shrapnel.services.factory.ExporterFactoryFactory;
import com.angrysurfer.social.shrapnel.services.service.ExportsService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Slf4j
@Getter
@Service
public class ExportServiceImpl implements ExportsService {

    @Resource
    ExporterFactoryFactory exportsRegistry;

    @Resource
    private List<ExporterFactory> exportFactories;

    public ExporterFactory getFactory(ExportRequest request) {
        return getExportFactories().stream().filter(fac -> fac.getExportName().equalsIgnoreCase(request.getExport())).findFirst()
                .orElseGet(() -> Objects.nonNull(exportsRegistry) && exportsRegistry.hasFactory(request) ? exportsRegistry.newInstance(request) : null);
    }

}
