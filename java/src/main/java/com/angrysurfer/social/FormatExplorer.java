package com.angrysurfer.social;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormatExplorer {


    public static void main(String[] args) {
        Integer value = new Integer(Integer.parseInt("1234"));
        try {
            String formatted = String.format("%s%06d", "T", value);
            log.info(formatted);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
