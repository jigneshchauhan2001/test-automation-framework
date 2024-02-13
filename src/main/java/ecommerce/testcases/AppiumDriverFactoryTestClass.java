package ecommerce.testcases;

import java.util.Set;

import javax.xml.xpath.XPath;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import framework.appium.core.AppiumDriverFactory;
import framework.core.Element;
import io.appium.java_client.android.AndroidDriver;

public class AppiumDriverFactoryTestClass extends AppiumDriverFactory{

	Element FILE_BTN = new Element("File button", "File", Element.NAME);
	
	Element AccessbilityBtn=new Element("Accessbility btn", "Access'ibility", Element.ACCESSIBILITYID);
	Element viewsBtn = new Element("Views btn", "//android.widget.TextView[@content-desc=\"Views\"]", Element.XPATH);
	Element webView = new Element("Web View Btn", "WebView2", Element.ACCESSIBILITYID);
	Element animationBtn = new Element("Web View Btn", "Animation", Element.ACCESSIBILITYID);
	Element textBoxWebView = new Element("Textbox webview", "//input[@id='i_am_a_textbox']", Element.XPATH);
	@Test(groups = "appium_notepad_test")
	public void notePadAppTest(){
		getAction().click(FILE_BTN);
	}
	

	
	@Test(groups = "appium_mobile_test")
	public void mobileAppTest(){
		getAction().click(viewsBtn);
		getAction().click(webView);
		getAction().switchContextForAndroid("WEBVIEW");
		getAction().getWebAction.sendKeys(textBoxWebView, "Hello Added Value in Textbox");
	}
}
