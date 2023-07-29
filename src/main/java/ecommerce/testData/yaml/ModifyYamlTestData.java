package ecommerce.testData.yaml;

import framework.utils.YamlUtils;

public class ModifyYamlTestData {
	
	public ItemDetails itemDetails;
	public CardDetails cardDetails;
	public static ModifyYamlTestData fetch(String testcaseName) {
		ModifyYamlTestData obj=(ModifyYamlTestData)YamlUtils.loadYaml(testcaseName, "test-data/ECOM_Register_User/Modify_Yaml_Test_Data.yaml");
		if (obj==null) {
			throw new RuntimeException("Data not found for the "+testcaseName + " in the yaml file.");
		}
		return obj;
	}
	
	
}
