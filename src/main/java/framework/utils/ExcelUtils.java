package framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	/**
	 * @Description this method opens the excel workbook from the given path.
	 *              supports both .xls and .xlsx
	 * @param excelPath
	 * @return Workbook
	 * @throws FileNotFoundException, FileFormatException
	 */
	public Workbook openExcelWorkBook(String excelPath) {
		Workbook workbook;
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(new File(excelPath));
		} catch (Exception e) {
			throw new RuntimeException("The specified excel not found in the path " + excelPath, e);
		}

		try {
			if (excelPath.toLowerCase().contains(".xlsx")) {
				workbook = new XSSFWorkbook(fileInputStream);
			} else if (excelPath.toLowerCase().contains(".xls")) {
				workbook = new HSSFWorkbook(fileInputStream);
			} else {
				throw new RuntimeException(
						excelPath + " File format is not supported. Only .xls / .xlsx will be supported");
			}
		} catch (Exception e) {
			workbook = null;
			throw new RuntimeException("Exception ocucred while opening the excel " + excelPath, e);
		}
		return workbook;
	}

	/**
	 * @Description this method opens the excel work sheet from the workbook based
	 *              on sheetname
	 * @param workbook
	 * @param sheetName
	 * @return excelSheet
	 * @throws RunTimeException
	 */
	public Sheet openExcleWorkSheet(Workbook workbook, String sheetName) {
		Sheet excelSheet;
		try {
			excelSheet = workbook.getSheet(sheetName);
		} catch (Exception e) {
			excelSheet = null;
			throw new RuntimeException(sheetName + " sheet not found in the excel", e);
		}
		return excelSheet;
	}

	/**
	 * @Description this method opens the excel work sheet from the workbook based
	 *              on sheet index
	 * @param workbook
	 * @param sheetIndex
	 * @return excelSheet
	 * @throws RunTimeException
	 */
	public Sheet openExcleWorkSheet(Workbook workbook, int sheetIndex) {
		Sheet excelSheet;
		try {
			excelSheet = workbook.getSheetAt(sheetIndex);
		} catch (Exception e) {
			excelSheet = null;
			throw new RuntimeException("No sheet found in the excel at index " + sheetIndex, e);
		}
		return excelSheet;
	}

	/**
	 * Description : this method closes the workbook
	 * 
	 * @param workbook
	 * @return void
	 */
	public void closeExcelWorkBook(Workbook workbook) {
		try {
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description : This method opens excel, writes the value in the cells with given row number and specified column name in the given excel work sheet. and then closes excel.
	 * @param excelPath
	 * @param excelSheetName
	 * @param columnName
	 * @param rowNumberCellValueMap
	 * @throws runTimeException
	 */
	public static void writeValueToExcelCells(String excelPath, String excelSheetName, String columnName, Map<Integer, String> rowNumberCellValueMap) {
		try {
			ExcelUtils excelUtils = new ExcelUtils();
			Workbook workbook = excelUtils.openExcelWorkBook(excelPath);
			Sheet sheet = excelUtils.openExcleWorkSheet(workbook, excelSheetName);
			
			DataFormatter dataFormatter=new DataFormatter();
			Map<String, Integer> columnNameIndexMap = new HashMap<>();
			Row headingRow = sheet.getRow(0);
			
			// mapping column name with its index
			for (int i = 0; i < headingRow.getLastCellNum(); i++) {
				columnNameIndexMap.put(dataFormatter.formatCellValue(headingRow.getCell(i)).toString(), i);
			}
			int columnIndex=columnNameIndexMap.get(columnName);
			
			// setting content in sheet which need to be write in excel
			for (Entry<Integer, String> entry : rowNumberCellValueMap.entrySet()) {
				sheet.getRow((int)entry.getKey()).createCell(columnIndex).setCellValue(entry.getValue());
			}
			
			// writing in excel sheet and closing output stream
			FileOutputStream fileOutputStream=new FileOutputStream(excelPath);
			workbook.write(fileOutputStream);
			fileOutputStream.close();
			
			// closing excel work book
			workbook.close();
		} catch (Exception e) {
			throw new RuntimeException("Exception occured while writing data to excel "+excelPath,e);
		}
	}

}
