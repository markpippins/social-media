package com.angrysurfer.social.shrapnel;

import com.angrysurfer.social.shrapnel.export.util.FileUtil;
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

    public static final String DELETE_ON_ERROR = "deleteOnError";

    public final static String FONTS_FOLDER = "fonts.folder";

    public final static String SQL_FOLDER = "sql.folder";

    private static Config instance;

    private Map<Object, Object> configurationMap = new HashedMap<>();

    private Config() {
        super();
    }

    public static synchronized Config getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Config();
            instance.init();
        }

        return instance;
    }

    public boolean containsKey(String key) {
        return getConfigurationMap().containsKey(key);
    }

    public void flush() {
        instance = null;
    }

    public Object getOrDefault(Object key, Object defaultValue) {
        return getConfigurationMap().getOrDefault(key, defaultValue);
    }

    public Object getProperty(Object key) {
        return getConfigurationMap().get(key);
    }

    public void init() {
        FileUtil.getFileProperties(DEFAULTS).entrySet().forEach(entry -> {
            getInstance().setProperty(entry.getKey(), entry.getValue());
        });
    }

    public void setProperty(Object key, Object value) {
        getConfigurationMap().put(key, value);
    }
}
