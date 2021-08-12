package com.angrysurfer.social.shrapnel.services.controller;

import com.angrysurfer.social.shrapnel.Config;
import com.angrysurfer.social.shrapnel.services.ExportRequest;
import com.angrysurfer.social.shrapnel.services.service.IExportsService;
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
    IExportsService exportsService;

    @PostMapping(value = "/fileExport")
    public ResponseEntity<ByteArrayResource> exportFile(@RequestBody ExportRequest request) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayResource bytes = null;

        if (exportsService.isValid(request))
            try {
                headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=%s.%s", request.getName(), request.getFileType()));
                bytes = exportsService.exportByteArrayResource(request);
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
        ByteArrayOutputStream stream = null;

        if (exportsService.isValid(request))
            try {
                headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=%s.%s", request.getName(), request.getFileType()));
                stream = exportsService.exportByteArrayOutputStream(request);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return ResponseEntity.notFound().build();
            }

        return Objects.nonNull(stream) ?
                ResponseEntity.ok().headers(headers).contentLength(stream.size()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(stream.toByteArray()) :
                ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/flushConfig")
    public ResponseEntity flushConfig() {
        Config.getInstance().flush();
        return ResponseEntity.ok().build();
    }
}
