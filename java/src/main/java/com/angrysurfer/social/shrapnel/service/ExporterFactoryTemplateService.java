package com.angrysurfer.social.shrapnel.service;

public interface ExporterFactoryTemplateService {
    boolean canExport(ExportRequest request);

    ExporterFactory getJdbcTemplateFactory(ExportRequest request);
}
