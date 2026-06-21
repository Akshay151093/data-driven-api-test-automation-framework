package reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {

    private static final Logger log = LoggerFactory.getLogger(PropertyFileReader.class);

    private static PropertyFileReader instance;
    private final Properties properties;

    public static PropertyFileReader getInstance() {
        if (instance == null) {
            try {
                instance = new PropertyFileReader();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    public PropertyFileReader() throws FileNotFoundException {
        properties = new Properties();
        try (InputStream reader = getClass().getClassLoader()
                .getResourceAsStream("src/test/resources/configurations/application.properties")) {
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
        String value = properties.getProperty(key);
        if (value == null || value.isEmpty()) {
            log.debug("Config | FAIL | key={} not found", key);
            throw new RuntimeException(key + " not specified in application.properties");
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
}
