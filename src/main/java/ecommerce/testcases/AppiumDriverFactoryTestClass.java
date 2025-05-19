package ecommerce.testcases;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.annotations.Test;

import framework.appium.core.AppiumDriverFactory;
import framework.core.Element;
import io.appium.java_client.android.nativekey.AndroidKey;

public class AppiumDriverFactoryTestClass extends AppiumDriverFactory{

	Element FILE_BTN = new Element("File button", "File", Element.NAME);
	
	Element AccessbilityBtn=new Element("Accessbility btn", "Access'ibility", Element.ACCESSIBILITYID);
	Element viewsBtn = new Element("Views btn", "//android.widget.TextView[@content-desc=\"Views\"]", Element.XPATH);
	Element webView = new Element("Web View Btn", "WebView2", Element.ACCESSIBILITYID);
	Element imageViewBtn = new Element("Web View Btn", "ImageView", Element.ACCESSIBILITYID);
	Element textBoxWebView = new Element("Textbox webview", "//input[@id='i_am_a_textbox']", Element.XPATH);
    Element scrollElement = new Element("Element", "//android.widget.ListView[@resource-id=\"android:id/list\"]", Element.XPATH);
    Element roundBtn = new Element("round btn", "//android.view.View[@resource-id=\"io.appium.android.apis:id/drag_dot_1\"]", Element.XPATH);
    Element dragBtn = new Element("drag btn", "Drag and Drop", Element.ACCESSIBILITYID);
    Element autoCompleteBtn = new Element("drag btn", "Auto Complete", Element.ACCESSIBILITYID);
    Element screenTopBtn = new Element("drag btn", "1. Screen Top", Element.ACCESSIBILITYID);
    Element editBtn = new Element("Edit btn", "android.widget.AutoCompleteTextView", Element.CLASS);
    
	@Test(groups = "appium_notepad_test")
	public void notePadAppTest(){
		getAction().click(FILE_BTN);
	}
	


	@Test(groups = "appium_mobile_test")
	public void mobileAppTest(){
//		getAction().click(viewsBtn);
//		getAction().getAndroidAction.swipeIntoView(scrollElement, webView, ActionConstants.SWIPE_UP);
//		getAction().click(webView);
//		//getAction().switchContextForAndroid("WEBVIEW");
//		getAction().getWebAction.sendKeys(textBoxWebView, "Hello Added Value in Textbox");
		
		
		getAction().click(viewsBtn).click(autoCompleteBtn).click(screenTopBtn);
		getAction().getWebAction.waitFor(3);
		AndroidKey[] androidKeys = {AndroidKey.A, AndroidKey.B,AndroidKey.C};
		getAction().getAndroidAction.performKeyOperations(androidKeys);
		getAction().getWebAction.waitFor(3);
		}
	
	
	

	

	
	
	
}
