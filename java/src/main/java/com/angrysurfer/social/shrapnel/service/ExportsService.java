package com.angrysurfer.social.shrapnel.service;

import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface ExportsService {

    ByteArrayResource exportByteArrayResource(ExportRequest request) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    ByteArrayOutputStream exportByteArrayOutputStream(ExportRequest request) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
