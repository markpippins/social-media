package com.angrysurfer.social.shrapnel.service.impl;

import com.angrysurfer.social.shrapnel.Exporter;
import com.angrysurfer.social.shrapnel.service.ExporterFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Getter
@Setter
@Slf4j
public class ExporterFactoryImpl implements ExporterFactory {

    private String exportName;

    private Class<Exporter> exportClass;

    private ExporterFactoryImpl(Class<Exporter> exportClass, String exportName) {
        setExportClass(exportClass);
        setExportName(exportName);
    }

    public static ExporterFactoryImpl newInstance(String exportClassName, String exportName) {
        try {
            Class<Exporter> clazz = (Class<Exporter>) Class.forName(exportName);
            return new ExporterFactoryImpl(clazz, exportName);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public Collection<Object> getData() {
        return null;
    }

}
