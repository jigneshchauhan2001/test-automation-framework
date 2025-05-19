package pfm.locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePageLocators {


	 	@FindBy(xpath = "//*[@aria-label='Close modal']")
	 	public WebElement signupCloseButton;

	    @FindBy(how = How.XPATH, using = "")
		public WebElement acceptCookiesButton;

	    @FindBy(xpath = "")
	    public  WebElement userName;
	
	   // use  By for presence methods & methods which uses presence inside it
	   public By signUpCloseButtonBy= By.xpath("//*[@aria-label='Close modal']");
	

}
