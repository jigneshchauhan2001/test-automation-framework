package ecommerce.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import ecommerce.actions.BaseAction;
import ecommerce.entities.Start;
import ecommerce.testData.yaml.LoginPageTestData;
import framework.core.WebDriverFactory;
import framework.testNG.RetryAnalyzer;
import framework.testNG.TestGroups;
import framework.utils.YamlUtils;

public class LoginPageTestCases extends WebDriverFactory {

	@Test(retryAnalyzer = RetryAnalyzer.class,groups = {TestGroups.DESKTOP_REGRESSION})
	public void LoginFirstTest(){
		
//		String testcaseName=new Exception().getStackTrace()[0].getMethodName();
//		LoginPageTestData loginPageTestData=LoginPageTestData.fetch(testcaseName);
		
		Start.
		asGuestUser
		.navigateToHomePage()
		._inHomePage();
	
//		System.out.println(loginPageTestData.itemDetails.email);
//		System.out.println(loginPageTestData.itemDetails.password);
//		System.out.println(loginPageTestData.cardDetails.cardNumber);
//		System.out.println(loginPageTestData.cardDetails.expiryYear);
//		System.out.println(loginPageTestData.cardDetails.expiryMonth);
//		System.out.println(loginPageTestData.cardDetails.cvv);
		
	}
	
	
	
	
}

