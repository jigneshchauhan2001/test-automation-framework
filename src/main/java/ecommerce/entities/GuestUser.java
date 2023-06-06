package ecommerce.entities;

import ecommerce.actions.HomePageAction;
import ecommerce.pagefactory.PageFactory;
import framework.core.BasePage;
import framework.utils.TestProperties;

public class GuestUser extends BasePage{

	PageFactory factory = new PageFactory();
	public HomePageAction navigateToHomePage() {
		factory.homePage().navigateToHomePage();
		return new HomePageAction();
	}
}
