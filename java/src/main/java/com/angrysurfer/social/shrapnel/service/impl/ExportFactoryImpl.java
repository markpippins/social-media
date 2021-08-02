package com.angrysurfer.social.shrapnel.service.impl;

import com.angrysurfer.social.shrapnel.Export;
import com.angrysurfer.social.shrapnel.ExportFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Getter
@Setter
@Slf4j
public class ExportFactoryImpl implements ExportFactory {

    private String exportName;

    private Class<Export> exportClass;

    private ExportFactoryImpl(Class<Export> exportClass, String exportName) {
        setExportClass(exportClass);
        setExportName(exportName);
    }

    public static ExportFactoryImpl newInstance(String exportClassName, String exportName) {
        try {
            Class<Export> clazz = (Class<Export>) Class.forName(exportName);
            return new ExportFactoryImpl(clazz, exportName);
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
