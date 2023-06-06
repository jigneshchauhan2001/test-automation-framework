package ecommerce.pages;

import org.testng.Assert;

import ecommerce.locators.HomePageLocators;
import ecommerce.pagefactory.PageFactory;
import framework.core.BasePage;
import framework.testNG.TestNGUtils;
import framework.utils.TestProperties;

public class HomePage extends BasePage{

	PageFactory factory = new PageFactory();
	
	public HomePage navigateToHomePage() {
		TestNGUtils.reportLog("Opening application URL. "+TestProperties.APPLICATION_URL.toString());
		getAction().getDriver().get(TestProperties.APPLICATION_URL.toString());
		factory.homePage()
		.acceptingBrowserCookeis()
		.clickingSignUpCloseButton();
		return this;
	}

	public HomePage clickingSignUpCloseButton() {
		TestNGUtils.reportLog("Clicking sign up close button in home page");
		getAction().WaitFor(3);
		getAction().Click(HomePageLocators.SIGN_UP_CLOSE_BUTTON);
		return this;
	}

	public HomePage acceptingBrowserCookeis() {
		TestNGUtils.reportLog("Accpeting browser cookies");
		getAction().WaitFor(3);
		getAction().Click(HomePageLocators.ACCEPT_COOKIES_BUTTON);
		return this;
	}
	
	
}
