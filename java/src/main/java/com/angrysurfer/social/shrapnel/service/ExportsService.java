package com.angrysurfer.social.shrapnel.service;

import com.angrysurfer.social.shrapnel.util.FileUtil;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public interface ExportsService {

    ExporterFactory getFactory(ExportRequest request);

    default ByteArrayResource exportByteArrayResource(ExportRequest request) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ExporterFactory factory = getFactory(request);
        return Objects.nonNull(factory) ? factory.exportByteArrayResource(request, FileUtil.makeFileName(request.getUser(), factory)) : null;
    }

    default ByteArrayOutputStream exportByteArrayOutputStream(ExportRequest request) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ExporterFactory factory = getFactory(request);
        return Objects.nonNull(factory) ? factory.exportByteArrayOutputStream(request) : null;
    }
}
