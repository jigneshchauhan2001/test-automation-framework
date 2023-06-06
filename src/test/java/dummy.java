import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class dummy {

	public static void main(String[] args) {

		// open url 
		// click btn
		// new tab
		// switch to tab
		// swich to parent tab
		
		
		WebDriver driver = new ChromeDriver();
		// opening url
		driver.get("url");
		//click btn 
		driver.findElement(By.xpath("btn xpath"));
		// swithcing to new opened tab
		Set<String> windows=driver.getWindowHandles();
		int count =0;
		String parentWindow = "";
		for (String window : windows) {
			if (count==1) {
				driver.switchTo().window(window);
			}
			if (count ==0) {
				parentWindow=window;
			}
			count++;
		}	
		// perform ops in tab 
		
		driver.switchTo().defaultContent();
		// or
		driver.switchTo().window(parentWindow);
	}

}
