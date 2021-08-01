package com.angrysurfer.shrapnel.service;

import com.angrysurfer.shrapnel.Export;
import com.angrysurfer.shrapnel.ExportFactory;

import java.util.Collection;

public interface ExportsRegistryService {
    boolean canExport(ExportRequest request);

    ExportFactory getFactory(ExportRequest request);
}
