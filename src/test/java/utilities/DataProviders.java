package utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {
    String file="TestData_Users";
    String sheet="Input_2";

    private static void printData(String[][] data) {
        for (String[] datum : data) {
            for (String s : datum) {
                System.out.print(s + "\t");
            }
            System.out.println();
        }
    }

    private static void printData(String[] data) {
        for (String s : data) {
            System.out.print(s + "\t");
        }
        System.out.println();
    }

    @DataProvider(name="payload")
    private String [][] getUserPayload(){
        int rowCount = ExcelUtils.getRowCount(file, sheet) - 1;  //1st row is header
        int colCount = ExcelUtils.getColumnCount(file, sheet);
        String[][] payload = new String[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                payload[i][j] = ExcelUtils.getCellValue(file, sheet, i + 1, j + 1);
            }
        }
        printData(payload);
        return payload;
    }

    @DataProvider(name="usernames")
    private String [] getUsernames(){
        int rowCount = ExcelUtils.getRowCount(file, sheet) - 1;
        int usernameCol = ExcelUtils.getColumnIndex(file, sheet, "Username");
        String[] usernames = new String[rowCount];
        for (int i = 0; i < rowCount; i++) {
            usernames[i] = ExcelUtils.getCellValue(file, sheet, i + 1, usernameCol);
        }
        printData(usernames);
        return usernames;
    }
}
