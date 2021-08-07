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

@Slf4j
@Getter
@Service
public class ExportServiceImpl implements ExportsService {

    @Resource
    List<ExporterFactoryFactory> exporterFactoryFactories;

    @Resource
    List<ExporterFactory> exporterFactories;

    public ExporterFactory getFactory(ExportRequest request) {
        return getExporterFactories().stream().filter(fac -> fac.getExportName().equalsIgnoreCase(request.getExport())).findFirst()
                .orElseGet(() -> factoryRegistered(request) ? getRegisteredFactory(request).newInstance(request) : null);
    }

    private ExporterFactoryFactory getRegisteredFactory(ExportRequest request) {
        return exporterFactoryFactories.stream().filter(fac -> fac.hasFactory(request)).findFirst().orElseGet(() -> null);
    }

    private boolean factoryRegistered(ExportRequest request) {
        return exporterFactoryFactories.stream().filter(fac -> fac.hasFactory(request)).findAny().isPresent();
    }


}
