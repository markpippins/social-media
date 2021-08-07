package com.angrysurfer.social.shrapnel.services.controller;

import com.angrysurfer.social.dto.UserDTO;
import com.angrysurfer.social.shrapnel.services.ExportRequest;
import com.angrysurfer.social.shrapnel.services.service.ExportsService;
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

    private static UserDTO user = new UserDTO() {
        @Override
        public String getAlias() {
            return "system-export";
        }
    };

    @Resource
    ExportsService exportsService;

    @PostMapping(value = "/fileExport")
    public ResponseEntity<ByteArrayResource> exportFile(@RequestBody ExportRequest request) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayResource bytes = null;

        if (isValid(request)) {
            request.setUser(user);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=%s.%s", request.getExport(), request.getFileType()));

            try {
                bytes = exportsService.exportByteArrayResource(request);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return Objects.nonNull(bytes) ?
                ResponseEntity.ok().headers(headers).contentLength(bytes.contentLength()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(bytes) :
                ResponseEntity.notFound().build();
    }

    private boolean isValid(ExportRequest request) {
        return true;
    }

    @PostMapping(value = "/streamExport")
    public ResponseEntity exportStream(@RequestBody ExportRequest request) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream stream = null;

        if (isValid(request)) {
            request.setUser(user);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=%s.%s", request.getExport(), request.getFileType()));

            try {
                stream = exportsService.exportByteArrayOutputStream(request);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return ResponseEntity.notFound().build();
            }
        }

        return Objects.nonNull(stream) ?
                ResponseEntity.ok().headers(headers).contentLength(stream.size()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(stream.toByteArray()) :
                ResponseEntity.notFound().build();
    }
}
