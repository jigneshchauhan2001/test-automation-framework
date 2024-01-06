package ecommerce.testData.yaml;

import framework.utils.YamlUtils;

public class LoginPageTestData {

	public ItemDetails itemDetails;
	public CardDetails cardDetails;
	
	
	/**
	 * Method to fetch test-data details from Login_Page.yaml file
	 * @param testcaseName(key)
	 * @return LoginPageTestData
	 */
	public static LoginPageTestData fetch(String testcaseName) {
		LoginPageTestData obj=(LoginPageTestData)YamlUtils.loadYaml(testcaseName, "test-data/ECOM_Register_User/Login_Page.yaml");
		if (obj==null) {
			throw new RuntimeException("Data not found for the "+testcaseName + " in the yaml file.");
		}
		return obj;
	}
	
	
	/**
	 * Method to update the value in Login_Page.yaml file
	 * @param key(testcaseName)
	 * @param data (Object)
	 */
	public static void updateTestDataYaml(String testcaseName,Object data) {
		YamlUtils.updateYaml(testcaseName, "test-data/ECOM_Register_User/Login_Page.yaml", data);
	}
	

}
