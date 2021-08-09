package com.angrysurfer.social.shrapnel.services;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ExportRequest {
    Map<String, Object> filterCriteria;
    private String name;
    private String fileType;
}
