package ecommerce.pages;

import org.testng.Assert;

import ecommerce.locators.HomePageLocators;
import ecommerce.pagefactory.PageFactory;
import ecommerce.testData.yaml.LoginPageTestData;
import framework.core.BasePage;
import framework.testNG.TestNGUtils;
import framework.utils.TestProperties;

public class HomePage extends BasePage{

	PageFactory factory = new PageFactory();
	
	public HomePage navigateToHomePage() {
		TestNGUtils.reportLog("Opening application URL. "+TestProperties.APPLICATION_URL.toString());
		getAction().getDriver().get(TestProperties.APPLICATION_URL.toString());
		factory.homePage();
//		.acceptingBrowserCookeis()
//		.clickingSignUpCloseButton();
		return this;
	}

	public HomePage clickingSignUpCloseButton() {
		TestNGUtils.reportLog("Clicking sign up close button in home page");
		getAction().click(HomePageLocators.SIGN_UP_CLOSE_BUTTON);
		return this;
	}
	
	
	// if any click or sendkeys failing due to stale-element reference error or element click intercepted, then you can use below logic
	public HomePage enterUserName(String userName) {
		TestNGUtils.reportLog("Entering username +"+userName);
		for (int i = 0; i < TestProperties.TEST_TIMEOUT.toInteger()/10; i++) {
			try {
				getAction().sendKeys(HomePageLocators.USER_NAME, userName);
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
		getAction().click(HomePageLocators.ACCEPT_COOKIES_BUTTON);
		return this;
	}

	
	public HomePage fetchOrderNumber(LoginPageTestData testData) {
		testData.itemDetails.orderNumber="R13432221";
		return this;
	}
	
	
}
