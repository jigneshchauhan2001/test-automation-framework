package ecommerce.testcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.Test;

import ecommerce.testData.yaml.ModifyYamlTestData;
import framework.core.WebDriverFactory;
import framework.testNG.RetryAnalyzer;
import framework.testNG.TestGroups;
import framework.utils.YamlUtils;

public class updateYamlFileDemo extends WebDriverFactory {
	
	@Test(retryAnalyzer = RetryAnalyzer.class,groups = {TestGroups.MODIFY_YAML_TESTS})
	public void modifyYamlFileTest() throws FileNotFoundException, IOException{	
		String testcaseName = new Exception().getStackTrace()[0].getMethodName();
		ModifyYamlTestData testdata = ModifyYamlTestData.fetch(testcaseName);

        // Update the email in the data object (optional, if you want to change the data)
		testdata.itemDetails.email = "updatednewemail";
		testdata.cardDetails.cardNumber = "updatednewcard";

      //   Update the YAML file with the updated data object
       YamlUtils.updateYaml(testcaseName, "test-data/ECOM_Register_User/Modify_Yaml_Test_Data.yaml", testdata);
       
        System.out.println("Printing updated card number");
        ModifyYamlTestData newObj = ModifyYamlTestData.fetch(testcaseName);
        System.out.println(newObj.cardDetails.cardNumber);
        System.out.println(newObj.itemDetails.email);
	}


}
