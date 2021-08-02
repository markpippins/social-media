package com.angrysurfer.shrapnel.service;

import com.angrysurfer.shrapnel.ExportFactory;

public interface ExportsRegistryService {
    boolean canExport(ExportRequest request);

    ExportFactory getFactory(ExportRequest request);
}
