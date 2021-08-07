package com.angrysurfer.social.shrapnel.service.factory;

import com.angrysurfer.social.shrapnel.service.ExportRequest;
import com.angrysurfer.social.shrapnel.service.factory.ExporterFactory;

public interface JdbcExporterFactoryFactory {
    boolean hasFactory(ExportRequest request);

    ExporterFactory newInstance(ExportRequest request);
}
