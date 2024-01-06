package ecommerce.actions;

import ecommerce.pagefactory.PageFactory;
import ecommerce.testData.yaml.LoginPageTestData;

public class HomePageAction extends BaseAction{

	PageFactory factory=new PageFactory();
	public HomePageAction fetchOrderNumber(LoginPageTestData testData) {
		factory.homePage()
		.fetchOrderNumber(testData);
		return this;
	}

}
