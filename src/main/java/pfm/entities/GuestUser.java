package pfm.entities;


import pfm.actions.HomePageAction;
import pfm.framework.core.BasePage;
import pfm.pagefactory.PageFactory;

public class GuestUser extends BasePage{

	PageFactory factory = new PageFactory();
	public HomePageAction navigateToHomePage() {
		factory.homePage().navigateToHomePage();
		return new HomePageAction();
	}
}
