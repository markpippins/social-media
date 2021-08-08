package com.angrysurfer.social.shrapnel.services.factory;

import com.angrysurfer.social.shrapnel.services.ExportRequest;

public interface MetaExportFactory {

    boolean hasFactory(ExportRequest request);

    ExportFactory newInstance(ExportRequest request);
}
