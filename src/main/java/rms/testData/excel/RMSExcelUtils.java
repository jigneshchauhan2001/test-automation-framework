package rms.testData.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.annotations.DataProvider;

import framework.utils.ExcelUtils;
import io.opentelemetry.sdk.metrics.data.PointData;

public class RMSExcelUtils {

	public static Map<String, POData> rmsExcelDataMap;
	public static ArrayList<String> getScenarioIds=new ArrayList<>();

	/**
	 * @Description this method will open given excel work book and sheets and will
	 *              read data from given excel sheets and will map it to its
	 *              respective class members. and then finally will close work book.
	 * @param rmsExcelPath
	 * @param POSheetName
	 * @param ItemDataSheetName
	 */
	public static void readRMSExcelSheets(String rmsExcelPath, String POSheetName, String ItemDataSheetName) {

		ExcelUtils excelUtils;
		Workbook workbook;
		Sheet poSheet;
		Sheet itemDataSheet;

		// opening rms workbook
		excelUtils = new ExcelUtils();
		try {
			workbook = excelUtils.openExcelWorkBook(rmsExcelPath);
		} catch (Exception e) {
			throw new RuntimeException("Exception occured while opening RMS excel workbook " + rmsExcelPath, e);
		}

		// opening rms poSheet
		try {
			poSheet = excelUtils.openExcleWorkSheet(workbook, POSheetName);
		} catch (Exception e) {
			throw new RuntimeException("Exception occured while opening RMS excel sheet " + POSheetName, e);
		}

		// initializing rmsExcelDataMap
		rmsExcelDataMap = new LinkedHashMap<>();

		// opening rms itemDataSheet
		try {
			itemDataSheet = excelUtils.openExcleWorkSheet(workbook, ItemDataSheetName);
		} catch (Exception e) {
			throw new RuntimeException("Exception occured while opening RMS excel sheet " + ItemDataSheetName, e);
		}

		// reading data from rms poSheet
		try {
			readTestDataFromRMSPOSheet(poSheet);
		} catch (Exception e) {
			throw new RuntimeException("Exception occured while reading data from RMS PO Sheet " + POSheetName, e);
		}

		// reading data from rms itemDataSheet
		try {
			readTestDataFromRMSItemDataSheet(itemDataSheet);
		} catch (Exception e) {
			throw new RuntimeException(
					"Exception occured while reading data from RMS ItemData Sheet " + ItemDataSheetName, e);
		}
		// closing rms workbook
		try {
			excelUtils.closeExcelWorkBook(workbook);
		} catch (Exception e) {
			throw new RuntimeException("Exception occured while closing workbook" + rmsExcelPath, e);
		}

	}

	public static void readTestDataFromRMSPOSheet(Sheet poSheet) {
		DataFormatter dataFormatter = new DataFormatter();
		Map<String, Integer> columnNameIndexMap = new HashMap<String, Integer>();
		Row headingsRow = poSheet.getRow(0);
		// mapping column name with its index
		for (int i = 0; i < headingsRow.getLastCellNum(); i++) {
			columnNameIndexMap.put(dataFormatter.formatCellValue(headingsRow.getCell(i)).toString(), i);
		}

		int rowCount = poSheet.getLastRowNum() - poSheet.getFirstRowNum();
		for (int i = 1; i <= rowCount; i++) {
			// created new object of POData class
			POData poData = new POData();

			poData.scenarioID = dataFormatter.formatCellValue(poSheet.getRow(i)
					.getCell(columnNameIndexMap.get(RMSProperties.EXCEL_PO_SHEET_COLUMN_SCENARIO_ID.toString())));
			// loading scenarioIDs to getScenario method
			getScenarioIds.add(poData.scenarioID);

			poData.scenarioDescription = dataFormatter.formatCellValue(poSheet.getRow(i).getCell(
					columnNameIndexMap.get(RMSProperties.EXCEL_PO_SHEET_COLUMN_SCENARIO_DESCRIPTION.toString())));

			poData.supplierID = dataFormatter.formatCellValue(poSheet.getRow(i)
					.getCell(columnNameIndexMap.get(RMSProperties.EXCEL_PO_SHEET_COLUMN_SUPPLIER_ID.toString())));

			poData.wareHouseNumber = dataFormatter.formatCellValue(poSheet.getRow(i)
					.getCell(columnNameIndexMap.get(RMSProperties.EXCEL_PO_SHEET_COLUMN_WAREHOUSE_NUMBER.toString())));

			poData.poNumber = dataFormatter.formatCellValue(poSheet.getRow(i)
					.getCell(columnNameIndexMap.get(RMSProperties.EXCEL_PO_SHEET_COLUMN_PO_NUMBER.toString())));

			// initialized List of ItemData. (created empty object of utem data)
			poData.itemDataList = new ArrayList<>();

			// adding poData object to rmsExcelDataMap
			rmsExcelDataMap.put(poData.scenarioID, poData);

			// setting rowNumber(0-based)
			poData.rowNumber = i;
		}
	}

	public static void readTestDataFromRMSItemDataSheet(Sheet itemDataSheet) {
		DataFormatter dataFormatter=new DataFormatter();
		Map<String, Integer> columnNameIndexMap = new HashMap<String, Integer>();
		Row headingsRow = itemDataSheet.getRow(0);
		// mapping column name with its index
		for (int i = 0; i < headingsRow.getLastCellNum(); i++) {
			columnNameIndexMap.put(dataFormatter.formatCellValue(headingsRow.getCell(i)).toString(), i);
		}
		
		int rowCount = itemDataSheet.getLastRowNum() - itemDataSheet.getFirstRowNum();
		
		for (int i = 1; i <= rowCount; i++) {
			String scenarioID = dataFormatter.formatCellValue(itemDataSheet.getRow(i)
					.getCell(columnNameIndexMap.get(RMSProperties.EXCEL_PO_SHEET_COLUMN_SCENARIO_ID.toString())));
			
			// finding poData object from map by its sceanrio ID
			POData poData = rmsExcelDataMap.get(scenarioID);
			
			// created object of Item-Data class
			ItemData itemData = new ItemData();
			//assigning itemID from data sheet to itemData Sheet
			itemData.itemID=dataFormatter.formatCellValue(itemDataSheet.getRow(i)
					.getCell(columnNameIndexMap.get(RMSProperties.EXCEL_ITEM_SHEET_COLUMN_ITEM_ID.toString())));
			
			itemData.itemQuantity=dataFormatter.formatCellValue(itemDataSheet.getRow(i)
					.getCell(columnNameIndexMap.get(RMSProperties.EXCEL_ITEM_SHEET_COLUMN_ITEM_QUANTITY.toString())));
			
			// adding itemData objject to respective poData object.
			poData.itemDataList.add(itemData);
			
			//assigning rowNumber
			itemData.rowNumber=i;
	
		}
	}
	
	// this method returns PO data Object for given particular scenario. 
	public static POData fetchData(String scenanrioID) {
		POData poData=rmsExcelDataMap.get(scenanrioID);
		return poData;
	}
	
	// this method return all scenario Ids and scenario descriptions
	public static Object[][] getAllSceanrioIdsAndDescriptions(){
		//creating 2d array for test-data provider
		Object[][] dPArray = new Object[getScenarioIds.size()][2];
		for (int i = 0; i < getScenarioIds.size(); i++) {
			dPArray[i][0]=getScenarioIds.get(i);
			dPArray[i][1]=fetchData(getScenarioIds.get(i)).scenarioDescription;
		}
		return dPArray;
	}
	
	
	@DataProvider(name = "RMSExcelDataProvider", parallel = true)
	public static Object[][] RMSExcelDataProvider(){
		RMSExcelUtils.readRMSExcelSheets(RMSProperties.EXCEL_PATH.toString(), RMSProperties.EXCEL_PO_SHEET_NAME.toString(), RMSProperties.EXCEL_ITEM_DATA_SHEET_NAME.toString());
		return getAllSceanrioIdsAndDescriptions();
	}
	
	
	public static void writingPONumberBackToPoSheet(String rmsExcelPath, String POSheetName, String PONumberColumnName,Map<Integer, String>rowNumberPONumber ) {
		ExcelUtils.writeValueToExcelCells(rmsExcelPath, POSheetName, PONumberColumnName, rowNumberPONumber);
	}
}
