package ecommerce.pagefactoryModel.testcases;

import org.testng.annotations.Test;

import ecommerce.entities.Start;
import ecommerce.testData.yaml.LoginPageTestData;

public class LoginPageTestCases {

	@Test
	public void loginTest() {
		
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
