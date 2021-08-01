package com.angrysurfer.shrapnel.service.controller;

import com.angrysurfer.shrapnel.service.ExportRequest;
import com.angrysurfer.shrapnel.service.ExportsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

@RestController
@RequestMapping("exports")
@Slf4j
public class ExportsController {

    @Resource
    ExportsService exportsService;

    @PostMapping(value = "/exportFile")
    public ResponseEntity<ByteArrayResource> exportFile(@RequestBody ExportRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=%s.%s", request.getExportName(), request.getFileType()));
        ByteArrayResource bytes = exportsService.exportByteArrayResource(request);
        return Objects.nonNull(bytes) ?
                ResponseEntity.ok().headers(headers).contentLength(bytes.contentLength()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(bytes) :
                ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/exportStream")
    public ResponseEntity<ByteArrayOutputStream> exportStream(@RequestBody ExportRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=%s.%s", request.getExportName(), request.getFileType()));
        ByteArrayOutputStream stream = exportsService.exportByteArrayOutputStream(request);
        return Objects.nonNull(stream) ?
                ResponseEntity.ok().headers(headers).contentLength(stream.size()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(stream) :
                ResponseEntity.notFound().build();
    }

}
