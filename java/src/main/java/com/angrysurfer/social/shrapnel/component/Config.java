package com.angrysurfer.social.shrapnel.component;

import com.angrysurfer.social.shrapnel.services.util.FileUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Getter
@Setter
public class Config {

    public static final String DEFAULTS = "java/src/main/resources/shrapnel.properties";

    private static Config instance;

    private Map<Object, Object> configurationMap = new HashedMap<>();

    private Config() {
        super();
    }

    public static synchronized Config getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Config();
            FileUtil.getFileProperties(DEFAULTS).entrySet().forEach(entry -> {
                instance.setProperty(entry.getKey(), entry.getValue());
            });
        }
        return instance;
    }

    public boolean containsKey(String key) {
        return getConfigurationMap().containsKey(key);
    }

    public Object getOrDefault(Object key, Object defaultValue) {
        return getConfigurationMap().getOrDefault(key, defaultValue);
    }

    public Object getProperty(Object key) {
        return getConfigurationMap().get(key);
    }

    public void setProperty(Object key, Object value) {
        getConfigurationMap().put(key, value);
    }
}
