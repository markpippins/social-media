package com.angrysurfer.social.shrapnel.export.factory;

import com.angrysurfer.social.shrapnel.export.service.Request;

public interface IMetaExportFactory {

    boolean hasFactory(Request request);

    IExportFactory newInstance(Request request);
}
