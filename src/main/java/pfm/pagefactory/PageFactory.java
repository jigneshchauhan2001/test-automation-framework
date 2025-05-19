package pfm.pagefactory;

import pfm.pages.HomePage;
import pfm.pages.LoginPage;

public class PageFactory {

	public LoginPage loginPage() {
		return new LoginPage();
	}
	
	public HomePage homePage() {
		return new HomePage();
	}
}
