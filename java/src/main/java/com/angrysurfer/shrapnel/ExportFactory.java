package com.angrysurfer.shrapnel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public interface ExportFactory {

    Collection<Object> getData();

    String getExportName();

    Class<Export> getExportClass();

    default Export newInstance() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(getExportClass().getCanonicalName());
        Constructor<?> ctor = clazz.getConstructor();
        return (Export) ctor.newInstance(new Object[]{});
    }
}
