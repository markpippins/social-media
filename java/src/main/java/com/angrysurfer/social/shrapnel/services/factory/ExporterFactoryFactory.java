package com.angrysurfer.social.shrapnel.services.factory;

import com.angrysurfer.social.shrapnel.services.ExportRequest;

public interface ExporterFactoryFactory {
    boolean hasFactory(ExportRequest request);

    ExporterFactory newInstance(ExportRequest request);
}