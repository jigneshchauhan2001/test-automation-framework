package pfm.actions;

import ecommerce.testData.yaml.LoginPageTestData;
import pfm.pagefactory.PageFactory;

public class HomePageAction extends BaseAction{

	PageFactory factory=new PageFactory();
	public HomePageAction fetchOrderNumber(LoginPageTestData testData) {
		factory.homePage()
		.fetchOrderNumber(testData);
		return this;
	}

}
