package com.angrysurfer.social.shrapnel.services.factory;

import com.angrysurfer.social.shrapnel.Exporter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public interface ExporterFactory {

    Collection<Object> getData();

    String getExportName();

    Class<Exporter> getExportClass();

    default Exporter newInstance() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(getExportClass().getCanonicalName());
        Constructor<?> ctor = clazz.getConstructor();
        return (Exporter) ctor.newInstance(new Object[]{});
    }
}
