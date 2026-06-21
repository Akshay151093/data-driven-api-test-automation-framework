package utils;

import config.ConfigManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;

public class ExcelUtils {

    private static final Logger logger = LogManagerUtil.getLogger(ExcelUtils.class);
    private static final DataFormatter DATA_FORMATTER = new DataFormatter(Locale.US);
    private static final String TEST_DATA_PATH = ConfigManager.getInstance().getTestDataPath();

    private ExcelUtils() {
    }

    private static String getPath(String fileName) {
        return TEST_DATA_PATH + fileName + ".xlsx";
    }

    private static Workbook openWorkbook(String fileName) {
        String path = getPath(fileName);
        logger.debug("Opening workbook: {}", path);
        try {
            return WorkbookFactory.create(new FileInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException("Unable to open Excel file: " + path, e);
        }
    }

    private static Sheet getSheet(Workbook workbook, String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new RuntimeException("Sheet not found: " + sheetName);
        }
        return sheet;
    }

    private static String getCellValue(Workbook workbook, Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == CellType.FORMULA) {
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            return DATA_FORMATTER.formatCellValue(cell, evaluator).trim();
        }
        return DATA_FORMATTER.formatCellValue(cell).trim();
    }

    private static boolean isRowEmpty(Workbook workbook, Row row) {
        if (row == null) {
            return true;
        }
        for (Cell cell : row) {
            if (!getCellValue(workbook, cell).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static int getRowCount(String fileName, String sheetName) {
        logger.debug("Calculating row count for file '{}' and sheet '{}'.", fileName, sheetName);
        try (Workbook workbook = openWorkbook(fileName)) {
            Sheet sheet = getSheet(workbook, sheetName);
            int count = sheet.getLastRowNum() + 1;
            while (count > 0 && isRowEmpty(workbook, sheet.getRow(count - 1))) {
                count--;
            }
            logger.debug("Sheet '{}' contains {} populated rows.", sheetName, count);
            return count;
        } catch (IOException e) {
            throw new RuntimeException("Error closing workbook for file: " + fileName, e);
        }
    }

    public static int getColumnCount(String fileName, String sheetName) {
        logger.debug("Calculating column count for file '{}' and sheet '{}'.", fileName, sheetName);
        try (Workbook workbook = openWorkbook(fileName)) {
            Sheet sheet = getSheet(workbook, sheetName);
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                return 0;
            }
            int count = 0;
            for (Cell cell : headerRow) {
                if (!getCellValue(workbook, cell).isEmpty()) {
                    count++;
                }
            }
            logger.debug("Sheet '{}' contains {} populated columns.", sheetName, count);
            return count;
        } catch (IOException e) {
            throw new RuntimeException("Error closing workbook for file: " + fileName, e);
        }
    }

    public static String getCellValue(String fileName, String sheetName, int rowNum, int colNum) {
        logger.trace("Reading cell [Sheet='{}', Row={}, Column={}]", sheetName, rowNum, colNum);
        try (Workbook workbook = openWorkbook(fileName)) {
            Sheet sheet = getSheet(workbook, sheetName);
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                return "";
            }
            Cell cell = row.getCell(colNum);
            return getCellValue(workbook, cell);
        } catch (IOException e) {
            throw new RuntimeException("Error closing workbook for file: " + fileName, e);
        }
    }

    public static int getColumnIndex(String fileName, String sheetName, String columnName) {
        logger.debug("Searching for column '{}'.", columnName);
        try (Workbook workbook = openWorkbook(fileName)) {
            Sheet sheet = getSheet(workbook, sheetName);
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Header row not found in sheet: " + sheetName);
            }
            for (Cell cell : headerRow) {
                String value = getCellValue(workbook, cell);
                if (value.equalsIgnoreCase(columnName.trim())) {
                    return cell.getColumnIndex();
                }
            }
            throw new RuntimeException("Column not found: " + columnName);
        } catch (IOException e) {
            throw new RuntimeException("Error closing workbook for file: " + fileName, e);
        }
    }

    public static LinkedHashMap<String, String> getDataFromRow(String fileName, String sheetName, String testDataLabel) {
        logger.info("Fetching test data for '{}'.", testDataLabel);
        try (Workbook workbook = openWorkbook(fileName)) {
            Sheet sheet = getSheet(workbook, sheetName);
            int rowNumber = findRowNumber(workbook, sheet, testDataLabel, 0);
            if (rowNumber == -1) {
                throw new RuntimeException("No test data found for: " + testDataLabel);
            }
            LinkedHashMap<String, String> data = new LinkedHashMap<>();
            Row labelRow = sheet.getRow(rowNumber);
            Row valueRow = sheet.getRow(rowNumber + 1);
            int col = 2;
            int maxColumns = 50;
            while (col < maxColumns) {
                String label = getCellValue(workbook, labelRow.getCell(col));
                if (label.isEmpty()) {
                    break;
                }
                String value = valueRow == null ? "" : getCellValue(workbook, valueRow.getCell(col));
                data.put(label, value);
                col++;
            }
            logger.info("Loaded {} data fields for '{}'.", data.size(), testDataLabel);
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Error closing workbook for file: " + fileName, e);
        }
    }

    private static int findRowNumber(
            Workbook workbook,
            Sheet sheet,
            String key,
            int columnIndex
    ) {
        for (Row row : sheet) {
            Cell cell = row.getCell(columnIndex);
            String text = getCellValue(workbook, cell);

            if (key.equals(text)) {
                return row.getRowNum();
            }
        }
        return -1;
    }
}