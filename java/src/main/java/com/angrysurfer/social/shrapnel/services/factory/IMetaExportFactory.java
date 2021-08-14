package com.angrysurfer.social.shrapnel.services.factory;

import com.angrysurfer.social.shrapnel.services.service.Request;

public interface IMetaExportFactory {

    boolean hasFactory(Request request);

    IExportFactory newInstance(Request request);
}
