package com.angrysurfer.social.shrapnel.services.factory;

import com.angrysurfer.social.shrapnel.services.ExportRequest;

public interface IMetaExportFactory {

    boolean hasFactory(ExportRequest request);

    IExportFactory newInstance(ExportRequest request);
}
