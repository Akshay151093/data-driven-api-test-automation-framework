package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;

public class ExcelUtils {

    private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static String currentFilePath;
    private static final String TEST_DATA_PATH = "testData/";
    private static final String EXCEL_EXTENSION = ".xlsx";

    private static String getPath(String fileName) {
        return TEST_DATA_PATH + fileName + EXCEL_EXTENSION;
    }

    private static void setSheet(String sheetName) {
        ExcelWSheet = ExcelWBook.getSheet(sheetName);
        if (ExcelWSheet == null) {
            throw new RuntimeException("Sheet not found: " + sheetName);
        }
    }

    public static void setExcelFile(String fileName, String sheetName) throws Exception {
        String path=getPath(fileName);
        if (ExcelWBook != null && path.equals(currentFilePath)) {
            setSheet(sheetName);
            return;
        }
        closeWorkbook();
        try (FileInputStream excelFile = new FileInputStream(path)) {
            ExcelWBook = new XSSFWorkbook(excelFile);
            ExcelWSheet = ExcelWBook.getSheet(sheetName);
            if (ExcelWSheet == null) {
                throw new RuntimeException("Sheet not found: " + sheetName);
            }
            ExcelWBook.setForceFormulaRecalculation(true);
            currentFilePath = path;
        }
    }

    private static Integer getRowNumber(String key, int col) {
        DataFormatter formatter = new DataFormatter();
        for (Row row : ExcelWSheet) {
            Cell cell = row.getCell(col);
            String text = formatter.formatCellValue(cell);
            if (key.equals(text)) {
                return row.getRowNum();
            }
        }
        return -1;
    }

    private static void closeWorkbook() {
        try {
            if (ExcelWBook != null) {
                ExcelWBook.close();
                ExcelWBook = null;
                ExcelWSheet = null;
                currentFilePath = null;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error closing workbook", e);
        }
    }

    private static String getExcelCellValue(int rowNum, int colNum) {
        try {
            Row row = ExcelWSheet.getRow(rowNum);
            if (row == null) {
                return "";
            }
            Cell cell = row.getCell(colNum);
            if (cell == null) {
                return "";
            }
            if (cell.getCellType() == CellType.FORMULA) {
                FormulaEvaluator evaluator = ExcelWBook.getCreationHelper().createFormulaEvaluator();
                cell = evaluator.evaluateInCell(cell);
            }
            return switch (cell.getCellType()) {
                case STRING -> cell.getStringCellValue();
                case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
                default -> new DataFormatter(Locale.US).formatCellValue(cell);
            };
        } catch (Exception e) {
            throw new RuntimeException("Error reading cell data", e);
        }
    }

    public static LinkedHashMap<String, String> getDataFromRow(String testDataLabel) {
        int rowNumber = getRowNumber(testDataLabel, 0);
        if (rowNumber == -1) {
            return null;
        }
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        int col = 2;
        int maxColumns = 50;
        while (col < maxColumns) {
            String label = getExcelCellValue(rowNumber, col);
            if (label == null || label.isEmpty()) {
                break;
            }
            String value = getExcelCellValue(rowNumber + 1, col);
            map.put(label, value);
            col++;
        }
        return map;
    }

    public static int getRowCount(String file, String sheet) {
        try {
            setExcelFile(file, sheet);
            int count = ExcelWSheet.getLastRowNum() + 1;
            while (count > 0) {
                Row row = ExcelWSheet.getRow(count - 1);
                if (row != null) {
                    boolean hasData = false;
                    for (Cell cell : row) {
                        if (!new DataFormatter().formatCellValue(cell).trim().isEmpty()) {
                            hasData = true;
                            break;
                        }
                    }
                    if (hasData) {
                        break;
                    }
                }
                count--;
            }
            return count;
        } catch (Exception e) {
            throw new RuntimeException("Error getting row count", e);
        }
    }

    public static int getColumnCount(String file, String sheet) {
        try {
            setExcelFile(file, sheet);
            Row headerRow = ExcelWSheet.getRow(0);
            if (headerRow == null) {
                return 0;
            }
            DataFormatter formatter = new DataFormatter();
            int count = 0;

            for (Cell cell : headerRow) {
                if (!formatter.formatCellValue(cell).trim().isEmpty()) {
                    count++;
                }
            }
            return count;
        } catch (Exception e) {
            throw new RuntimeException("Error getting column count", e);
        }
    }

    public static String getCellValue(String file, String sheet, int rowNum, int colNum) {
        try {
            setExcelFile(file, sheet);
            return getExcelCellValue(rowNum, colNum);
        } catch (Exception e) {
            throw new RuntimeException(
                    String.format("Error reading cell [%d, %d] from sheet '%s'", rowNum, colNum, sheet),
                    e);
        }
    }

    public static int getColumnIndex(String file, String sheet, String columnName) {
        try {
            setExcelFile(file, sheet);
            Row headerRow = ExcelWSheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Header row not found.");
            }
            DataFormatter formatter = new DataFormatter();
            for (Cell cell : headerRow) {
                String value = formatter.formatCellValue(cell).trim();
                if (value.equalsIgnoreCase(columnName.trim())) {
                    return cell.getColumnIndex();
                }
            }
            throw new RuntimeException("Column not found: " + columnName);
        } catch (Exception e) {
            throw new RuntimeException("Error getting column index for: " + columnName, e);
        }
    }
}
