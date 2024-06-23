package pfm.framework.core;

import org.openqa.selenium.WebDriver;
import framework.core.WebDriverFactory;

public class BasePage {

	
	public WebDriver getCurrentDriver() {
		return WebDriverFactory.getDriver();
	}
	
	public Action getAction() {
		return WebDriverFactory.getPFMAction();
	}
	
	
}
