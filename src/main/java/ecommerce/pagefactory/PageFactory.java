package ecommerce.pagefactory;
import ecommerce.pages.HomePage;
import ecommerce.pages.LoginPage;

public class PageFactory {

	public LoginPage loginPage() {
		return new LoginPage();
	}
	
	public HomePage homePage() {
		return new HomePage();
	}
}
