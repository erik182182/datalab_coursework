package ru.erik182.datalab.managers;

import org.apache.log4j.Logger;
import ru.erik182.datalab.Application;
import ru.erik182.datalab.models.MaskMode;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MaskModeManager {

    private MaskModeManager() {
    }

    private static Properties properties;
    private static final String PROPERTIES_URL = "src/main/resources/masks.properties";
    private static final Logger log = Logger.getLogger(Application.class);

    static {
        // property file init
        FileInputStream fis;
        properties = new Properties();

        try {
            fis = new FileInputStream(PROPERTIES_URL);
            properties.load(fis);
        } catch (IOException e) {
            log.error("Property initialization error", e);
        }
    }

    public static MaskMode getMaskMode(String name) {
        String regexp = properties.getProperty(properties.getProperty("masks.regexp") + name);
        String result = properties.getProperty(properties.getProperty("masks.result") + name);
        if (regexp == null || result == null) {
            return null;
        }
        return MaskMode.builder()
                .name(name)
                .regexp(regexp)
                .result(result)
                .build();
    }

}
