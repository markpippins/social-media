package com.angrysurfer.social.shrapnel.services.service;

import com.angrysurfer.social.shrapnel.services.ExportRequest;
import com.angrysurfer.social.shrapnel.services.factory.IExportFactory;
import com.angrysurfer.social.shrapnel.services.factory.IMetaExportFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Getter
@Service
public class ExportsService implements IExportsService {

    @Resource
    List<IMetaExportFactory> metaFactories;

    @Resource
    List<IExportFactory> exporterFactories;

    private boolean factoryRegistered(ExportRequest request) {
        return metaFactories.stream().anyMatch(fac -> fac.hasFactory(request));
    }

    private IMetaExportFactory getMetaFactory(ExportRequest request) {
        return metaFactories.stream().filter(fac -> fac.hasFactory(request)).findFirst().orElseGet(() -> null);
    }

    public IExportFactory getFactory(ExportRequest request) {
        return getExporterFactories().stream().filter(fac -> fac.getExportName().equalsIgnoreCase(request.getName())).findFirst()
                .orElseGet(() -> factoryRegistered(request) ? getMetaFactory(request).newInstance(request) : null);
    }
}
