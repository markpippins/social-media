package com.angrysurfer.shrapnel.service;

import com.angrysurfer.shrapnel.component.property.ColumnSpec;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.List;

public interface ExportsService {

    ByteArrayResource exportByteArrayResource(ExportRequest request);

    ByteArrayOutputStream exportByteArrayOutputStream(ExportRequest request);
}
