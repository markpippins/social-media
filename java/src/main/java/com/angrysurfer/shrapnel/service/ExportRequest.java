package com.angrysurfer.shrapnel.service;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ExportRequest {
    private String exportName;
    private String fileType;
    Map<String, Object> filterCriteria;
}