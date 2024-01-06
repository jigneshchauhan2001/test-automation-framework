package framework.appium.core;

import io.appium.java_client.AppiumDriver;

public class BasePage {

	public AppiumDriver getCurrentDriver() {
		return AppiumDriverFactory.getDriver();
	}
	
	public Action getAction() {
		return AppiumDriverFactory.getAction();
	}
}
