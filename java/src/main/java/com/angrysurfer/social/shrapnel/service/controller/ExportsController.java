package com.angrysurfer.social.shrapnel.service.controller;

import com.angrysurfer.social.shrapnel.service.ExportRequest;
import com.angrysurfer.social.shrapnel.service.ExportsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @PostMapping(value = "/fileExport")
    public ResponseEntity<ByteArrayResource> exportFile(@RequestBody ExportRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=%s.%s", request.getExportName(), request.getFileType()));
        ByteArrayResource bytes = null;

        try {
            exportsService.exportByteArrayResource(request);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return Objects.nonNull(bytes) ?
                ResponseEntity.ok().headers(headers).contentLength(bytes.contentLength()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(bytes) :
                ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/streamExport")
    public ResponseEntity exportStream(@RequestBody ExportRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=%s.%s", request.getExportName(), request.getFileType()));

        try {
            ByteArrayOutputStream stream = exportsService.exportByteArrayOutputStream(request);
            return ResponseEntity.ok().headers(headers).contentLength(stream.size()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(stream.toByteArray());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }
}
