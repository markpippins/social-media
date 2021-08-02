package com.angrysurfer.social.service;

import com.angrysurfer.shrapnel.ExportFactory;
import com.angrysurfer.shrapnel.service.ExportRequest;
import com.angrysurfer.shrapnel.service.ExportsRegistryService;
import org.springframework.stereotype.Service;

@Service
public class ExportsRegistryServiceImpl implements ExportsRegistryService {

    @Override
    public boolean canExport(ExportRequest request) {
        return false;
    }

    @Override
    public ExportFactory getFactory(ExportRequest request) {
        return null;
    }
}
