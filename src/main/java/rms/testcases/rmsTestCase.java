package rms.testcases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import framework.core.WebDriverFactory;
import framework.testNG.RetryAnalyzer;
import framework.testNG.TestGroups;
import rms.testData.excel.ItemData;
import rms.testData.excel.POData;
import rms.testData.excel.RMSExcelUtils;
import rms.testData.excel.RMSProperties;

public class rmsTestCase extends WebDriverFactory{

	@Test(groups = {TestGroups.RMS_TEST},dataProvider = "RMSExcelDataProvider",dataProviderClass = RMSExcelUtils.class)
	public static void rmsFirstTestCase(String scenarioID,String scenarioDescription) {
		
			POData poData = RMSExcelUtils.fetchData(scenarioID);
			System.out.println(poData.scenarioID);
			System.out.println(poData.scenarioDescription);
			System.out.println(poData.supplierID);
			System.out.println(poData.wareHouseNumber);
			
			for (int j = 0; j < poData.itemDataList.size(); j++) {
				 ItemData itemData=poData.itemDataList.get(j); 
				 System.out.println(itemData.itemID);
				 System.out.println(itemData.itemQuantity);
			}
		
		// testing write method
		Map<Integer, String> rowNumberPoNumberMap = new HashMap<>();
		rowNumberPoNumberMap.put(1, "12345");
		rowNumberPoNumberMap.put(2, "12346");
		rowNumberPoNumberMap.put(3, "12347");
		rowNumberPoNumberMap.put(4, "12348");
		rowNumberPoNumberMap.put(5, "12349");
		RMSExcelUtils.writingPONumberBackToPoSheet(RMSProperties.EXCEL_PATH.toString(),  RMSProperties.EXCEL_PO_SHEET_NAME.toString(), RMSProperties.EXCEL_PO_SHEET_COLUMN_PO_NUMBER.toString(), rowNumberPoNumberMap);
		
	}

}
