package com.angrysurfer.social.shrapnel.service.impl;

import com.angrysurfer.social.shrapnel.service.ExporterFactory;
import com.angrysurfer.social.shrapnel.service.ExportRequest;
import com.angrysurfer.social.shrapnel.service.ExporterFactoryTemplateService;
import com.angrysurfer.social.shrapnel.service.ExportsService;
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
    ExporterFactoryTemplateService exportsRegistry;

    @Resource
    private List<ExporterFactory> exportFactories;

    public ExporterFactory getFactory(ExportRequest request) {
        return getExportFactories().stream().filter(fac -> fac.getExportName().equalsIgnoreCase(request.getExportName())).findFirst()
                .orElseGet(() -> Objects.nonNull(exportsRegistry) && exportsRegistry.canExport(request) ? exportsRegistry.getJdbcTemplateFactory(request) : null);
    }

}
