package com.angrysurfer.social.shrapnel.services.controller;

import com.angrysurfer.social.shrapnel.Config;
import com.angrysurfer.social.shrapnel.services.service.IExportsService;
import com.angrysurfer.social.shrapnel.services.service.Request;
import com.angrysurfer.social.shrapnel.services.service.validation.IRequestValidator;
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

    @Resource
    IRequestValidator exportRequestValidator;

    @PostMapping(value = "/fileExport")
    public ResponseEntity<ByteArrayResource> exportFile(@RequestBody Request request) {
        exportRequestValidator.validate(request);
        HttpHeaders headers = new HttpHeaders();
        ByteArrayResource bytes = null;
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=%s.%s", request.getName(), request.getFileType()));
        bytes = exportsService.exportByteArrayResource(request);
        return Objects.nonNull(bytes) ?
                ResponseEntity.ok().headers(headers).contentLength(bytes.contentLength()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(bytes) :
                ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/streamExport")
    public ResponseEntity exportStream(@RequestBody Request request) {
        exportRequestValidator.validate(request);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=%s.%s", request.getName(), request.getFileType()));
        ByteArrayOutputStream stream = exportsService.exportByteArrayOutputStream(request);
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
