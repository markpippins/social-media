package com.angrysurfer.social.shrapnel.services.service.impl;

import com.angrysurfer.social.shrapnel.services.ExportRequest;
import com.angrysurfer.social.shrapnel.services.factory.ExportFactory;
import com.angrysurfer.social.shrapnel.services.factory.MetaExportFactory;
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
    List<MetaExportFactory> metaFactories;

    @Resource
    List<ExportFactory> exporterFactories;

    public ExportFactory getFactory(ExportRequest request) {
        return getExporterFactories().stream().filter(fac -> fac.getExportName().equalsIgnoreCase(request.getExport())).findFirst()
                .orElseGet(() -> factoryRegistered(request) ? getRegisteredFactory(request).newInstance(request) : null);
    }

    private MetaExportFactory getRegisteredFactory(ExportRequest request) {
        return metaFactories.stream().filter(fac -> fac.hasFactory(request)).findFirst().orElseGet(() -> null);
    }

    private boolean factoryRegistered(ExportRequest request) {
        return metaFactories.stream().filter(fac -> fac.hasFactory(request)).findAny().isPresent();
    }


}
