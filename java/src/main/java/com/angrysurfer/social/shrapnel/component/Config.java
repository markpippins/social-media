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

    private static Config instance;

    private Map<Object, Object> configurationMap = new HashedMap<>();

    private Config() {
        super();
        init();
    }

    public static Config getInstance() {
        if (Objects.isNull(instance))
            instance = new Config();

        return instance;
    }

    private void init() {
        FileUtil.getFileProperties(FileUtil.DEFAULTS).entrySet().forEach(entry -> {
            Config.getInstance().setProperty(entry.getKey(), entry.getValue());
        });
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
