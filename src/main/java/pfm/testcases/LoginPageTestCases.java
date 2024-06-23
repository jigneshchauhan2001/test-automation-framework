package pfm.testcases;


import org.testng.annotations.Test;

import ecommerce.testData.yaml.LoginPageTestData;
import framework.core.WebDriverFactory;
import pfm.entities.Start;

public class LoginPageTestCases extends WebDriverFactory{

	@Test(groups = "PFM_Test")
	public void loginTestForPFM() {	
		String testcaseName=new Exception().getStackTrace()[0].getMethodName();
		LoginPageTestData testData=LoginPageTestData.fetch(testcaseName);
		Start.
		asGuestUser
		.navigateToHomePage()
		._inHomePage()
		.fetchOrderNumber(testData);
		
		// update yaml
		LoginPageTestData.updateTestDataYaml(testcaseName, testData);	
		
	}
}
