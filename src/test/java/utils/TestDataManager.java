package utils;

import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class TestDataManager {

    private static final Logger logger = LogManagerUtil.getLogger(TestDataManager.class);

    private static void validateNotEmpty(Map<?, ?> data, String message) {
        if (data == null || data.isEmpty()) {
            throw new IllegalStateException(message);
        }
    }

    public LinkedHashMap<String, String> getTestData(String testDataLabel, String file, String sheet) {
        logger.info("Retrieving test data '{}'.", testDataLabel);
        Objects.requireNonNull(testDataLabel, "testDataLabel");
        Objects.requireNonNull(file, "file");
        Objects.requireNonNull(sheet, "sheet");
        String trimmedLabel = testDataLabel.trim();
        LinkedHashMap<String, String> data = ExcelUtils.getDataFromRow(
                file,
                sheet,
                trimmedLabel
        );
        validateNotEmpty(data, "No test data found for: " + trimmedLabel);
        logger.info("Test data '{}' retrieved successfully.", trimmedLabel);
        return data;
    }
}