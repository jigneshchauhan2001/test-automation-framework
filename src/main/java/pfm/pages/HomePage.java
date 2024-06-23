package pfm.pages;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


import ecommerce.testData.yaml.LoginPageTestData;
import framework.testNG.TestNGUtils;
import framework.utils.TestProperties;
import pfm.framework.core.BasePage;
import pfm.locators.HomePageLocators;


public class HomePage extends BasePage{

   public HomePageLocators homePageLocators;

 
    public HomePage() {
    	this.homePageLocators = new HomePageLocators();
        PageFactory.initElements(getAction().getDriver(), homePageLocators);
	}
	
	public HomePage navigateToHomePage() {
		TestNGUtils.reportLog("Opening application URL. "+TestProperties.APPLICATION_URL.toString());
		getAction().getDriver().get(TestProperties.APPLICATION_URL.toString());
//		acceptingBrowserCookeis()
		clickingSignUpCloseButton();
		return this;
	}

	
	public HomePage clickingSignUpCloseButton() {
		TestNGUtils.reportLog("Clicking sign up close button in home page");
		getAction().waitForElementPresence(homePageLocators.signUpCloseButtonBy);
		getAction().click(homePageLocators.signupCloseButton);
		return this;
	}
	
	
	// if any click or sendkeys failing due to stale-element reference error or element click intercepted, then you can use below logic
	public HomePage enterUserName(String userName) {
		TestNGUtils.reportLog("Entering username +"+userName);
		for (int i = 0; i < TestProperties.TEST_TIMEOUT.toInteger()/10; i++) {
			try {
				homePageLocators.userName.sendKeys(userName);
				break;
			} catch (Exception e) {
				getAction().waitFor(10);
			}
		}
		return this;
	}

	public HomePage acceptingBrowserCookeis() {
		TestNGUtils.reportLog("Accpeting browser cookies");
		getAction().waitFor(3);
		homePageLocators.acceptCookiesButton.click();
		return this;
	}

	
	public HomePage fetchOrderNumber(LoginPageTestData testData) {
		testData.itemDetails.orderNumber="R13432221";
		return this;
	}
	
	
}
