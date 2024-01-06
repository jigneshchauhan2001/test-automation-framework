package ecommerce.locators;

import framework.core.Element;

public class HomePageLocators {

	public static Element SIGN_UP_CLOSE_BUTTON=new Element("Signup close button", "//*[@data-cy='registerModal__closeButton']", Element.XPATH);
	public static Element ACCEPT_COOKIES_BUTTON=new Element("Accept cookies button", "//*[@id='onetrust-accept-btn-handler']", Element.XPATH);
	public static Element USER_NAME=new Element("Username", "", Element.XPATH);
	

}
