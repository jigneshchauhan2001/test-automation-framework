package pfm.actions;

public class BaseAction {

	public LoginPageAction _inLoginPage() {
		return new LoginPageAction();
	}
	
	public HomePageAction _inHomePage() {
		return new HomePageAction();
	}
}
