package com.angrysurfer.social.shrapnel.services;

import com.angrysurfer.social.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ExportRequest {
    Map<String, Object> filterCriteria;
    private String export;
    private String fileType;
    private UserDTO user;
}
