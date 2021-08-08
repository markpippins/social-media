package com.angrysurfer.social.shrapnel.services.factory;

import com.angrysurfer.social.shrapnel.services.Export;
import com.angrysurfer.social.shrapnel.services.TabularExport;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public interface ExportFactory {

    Collection<Object> getData();

    String getExportName();

    default Class getExportClass() {
        return TabularExport.class;
    }

    default Export newInstance() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(getExportClass().getCanonicalName());
        Constructor<?> ctor = clazz.getConstructor();
        return (Export) ctor.newInstance(new Object[]{});
    }
}
