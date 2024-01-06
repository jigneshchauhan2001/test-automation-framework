package ecommerce.testcases;

import org.testng.annotations.Test;

import framework.appium.core.AppiumDriverFactory;
import framework.core.Element;

public class AppiumDriverFactoryTestClass extends AppiumDriverFactory{

	Element FILE_BTN = new Element("File button", "File", Element.NAME);
			
	
	@Test(groups = "appium_test")
	public void notePadAppTest(){
		getAction().Click(FILE_BTN);
	}
}
