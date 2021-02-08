package ru.erik182.datalab.managers;

import org.apache.log4j.Logger;
import ru.erik182.datalab.Application;
import ru.erik182.datalab.models.MaskMode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MaskModeManager {

    private static final Logger log = Logger.getLogger(Application.class);
    private static final Properties properties;

    static {
        // property file init
        InputStream is;
        properties = new Properties();

        try {
            is = Application.class.getClassLoader().getResourceAsStream("masks.properties");
            properties.load(is);
        } catch (IOException e) {
            log.error("Property initialization error", e);
        }
    }

    private MaskModeManager() {
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
