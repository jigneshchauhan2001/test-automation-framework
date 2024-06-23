package framework.core;

import org.openqa.selenium.WebDriver;

public class BasePage {

	public WebDriver getCurrentDriver() {
		return WebDriverFactory.getDriver();
	}
	
	public Action getAction(){
		return WebDriverFactory.getAction();
	}
	
}
