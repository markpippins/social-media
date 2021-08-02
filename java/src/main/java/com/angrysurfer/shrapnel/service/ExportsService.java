package com.angrysurfer.shrapnel.service;

import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;

public interface ExportsService {

    ByteArrayResource exportByteArrayResource(ExportRequest request);

    ByteArrayOutputStream exportByteArrayOutputStream(ExportRequest request);
}
