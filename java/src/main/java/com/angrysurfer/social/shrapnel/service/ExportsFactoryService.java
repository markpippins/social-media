package com.angrysurfer.social.shrapnel.service;

import com.angrysurfer.social.shrapnel.ExportFactory;

public interface ExportsFactoryService {
    boolean canExport(ExportRequest request);

    ExportFactory getFactory(ExportRequest request);
}
