package com.angrysurfer.social.shrapnel.export.factory;

import com.angrysurfer.social.shrapnel.export.Export;
import com.angrysurfer.social.shrapnel.export.IExport;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;

public interface IExportFactory {

    default Collection<Object> getData() {
        return Collections.EMPTY_LIST;
    }

    String getExportName();

    default Class getExportClass() {
        return Export.class;
    }

    default IExport newInstance() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(getExportClass().getCanonicalName());
        Constructor<?> ctor = clazz.getConstructor();
        return (IExport) ctor.newInstance(new Object[]{});
    }
}
