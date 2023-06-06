package ecommerce.testData.yaml;

import framework.utils.YamlUtils;

public class LoginPageTestData {

	public ItemDetails itemDetails;
	public CardDetails cardDetails;
	public static LoginPageTestData fetch(String testcaseName) {
		LoginPageTestData obj=(LoginPageTestData)YamlUtils.loadYaml(testcaseName, "test-data/ECOM_Register_User/Login_Page.yaml");
		if (obj==null) {
			throw new RuntimeException("Data not found for the "+testcaseName + " in the yaml file.");
		}
		return obj;
	}
	

}
