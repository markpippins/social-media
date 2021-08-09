package com.angrysurfer.social.shrapnel.services.factory;

import com.angrysurfer.social.shrapnel.component.Export;
import com.angrysurfer.social.shrapnel.component.TabularExport;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;

public interface ExportFactory {

    default Collection<Object> getData() {
        return Collections.EMPTY_LIST;
    }

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
