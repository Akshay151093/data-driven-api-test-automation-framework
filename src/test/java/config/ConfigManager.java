package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);
    private static ConfigManager instance;
    private final Properties properties;

    public static ConfigManager getInstance() {
        if (instance == null) {
            try {
                instance = new ConfigManager();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    private ConfigManager() throws FileNotFoundException {
        properties = new Properties();
        try (InputStream reader = getClass().getClassLoader()
                .getResourceAsStream("configurations/application.properties")) {
            if (reader == null) {
                log.debug("Config | FAIL | classpath resource=configurations/application.properties");
                throw new FileNotFoundException("application.properties not found in classpath.");
            }
            properties.load(reader);
            log.debug("Config | SUCCESS | load | classpath resource=configurations/application.properties");
        } catch (IOException e) {
            log.debug("Config | FAIL | load | {}", e.getClass().getSimpleName());
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    private String getProperty(String key) {
        String value = System.getProperty(key);
        if (value == null || value.isEmpty()) {
            value = System.getenv(key);
        }
        if (value == null || value.isEmpty()) {
            value = properties.getProperty(key);
        }
        if (value == null || value.isEmpty()) {
            throw new RuntimeException(key + " not specified in application.properties, system properties, or environment variables");
        }
        log.debug("getProperty | SUCCESS | key={} | gotValue={}", key, value);
        return value;
    }

    public String getBaseUrl() {
        return getProperty("base_url");
    }

    public String getTestDataPath() {
        return getProperty("testDataFilePath");
    }

    public String getTestDataFileName() {
        return getProperty("testDataFile");
    }

    public String getDataProviderSheet() {
        return getProperty("dataProviderSheet");
    }

    public String getMapDataSheet() {
        return getProperty("mapDataSheet");
    }
}
