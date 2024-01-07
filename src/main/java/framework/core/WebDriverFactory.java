package framework.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import framework.utils.TestProperties;

public class WebDriverFactory {
	private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<WebDriver>();
	private static ThreadLocal<HashMap<String, Object>> hashMapLocal = new ThreadLocal<HashMap<String,Object>>() {
		@Override
		protected HashMap<String, Object> initialValue(){
			return new HashMap<>();
		}
	};
	
	public static WebDriver getDriver() { 
		return driverThreadLocal.get();
	}
	
	public static Action getAction() {
		return new Action(driverThreadLocal.get(), hashMapLocal.get());
	}
	
	
	
	@BeforeMethod(alwaysRun = true)
	public void assignDriver() throws MalformedURLException {
		
		if (TestProperties.TEST_BROWSER.toString().toLowerCase().contains("chrome")) {
			ChromeOptions chromeOptions = new ChromeOptions();
			driverThreadLocal.set(new RemoteWebDriver(new URL("http://"+TestProperties.SELENIUM_HOST+":"+TestProperties.SELENIUM_HUB_PORT+"/wd/hub"),chromeOptions,false));
			driverThreadLocal.get().manage().window().maximize();
		}
		else if (TestProperties.TEST_BROWSER.toString().toLowerCase().contains("ff") || TestProperties.TEST_BROWSER.toString().toLowerCase().contains("firefox")) {
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			driverThreadLocal.set(new RemoteWebDriver(new URL("http://"+TestProperties.SELENIUM_HOST+":"+TestProperties.SELENIUM_HUB_PORT+"/wd/hub"),firefoxOptions,false));
			driverThreadLocal.get().manage().window().maximize();
		}
		else if (TestProperties.TEST_BROWSER.toString().toLowerCase().contains("edge") ) {
			EdgeOptions edgeOptions = new EdgeOptions();
			driverThreadLocal.set(new RemoteWebDriver(new URL("http://"+TestProperties.SELENIUM_HOST+":"+TestProperties.SELENIUM_HUB_PORT+"/wd/hub"),edgeOptions,false));
			driverThreadLocal.get().manage().window().maximize();
		}
		else if (TestProperties.TEST_BROWSER.toString().toLowerCase().contains("ie") || TestProperties.TEST_BROWSER.toString().toLowerCase().contains("explor")) {
			InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
			internetExplorerOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			driverThreadLocal.set(new RemoteWebDriver(new URL("http://"+TestProperties.SELENIUM_HOST+":"+TestProperties.SELENIUM_HUB_PORT+"/wd/hub"),internetExplorerOptions,false));
			driverThreadLocal.get().manage().window().maximize();
		}
		else if (TestProperties.TEST_BROWSER.toString().toLowerCase().contains("mobile")) {
			// mobile execution setup through mobile emulation in chrome browser
			Map<String, String> mobileHashMap = new HashMap<>();
			mobileHashMap.put("deviceName", "Samsung Galaxy S20 Ultra");
			// if u pass "mobileEmulation" in setExperimentalOption method it will do setup based on hashmap you have passed.
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setExperimentalOption("mobileEmulation", mobileHashMap);
			driverThreadLocal.set(new RemoteWebDriver(new URL("http://"+TestProperties.SELENIUM_HOST+":"+TestProperties.SELENIUM_HUB_PORT+"/wd/hub"),chromeOptions,false));
			// created and setted this variable as u can you it for action class to handle mobile execution
			TestProperties.MOBILE_EXECUTUION.setProperty(true);
		}
		else {
			throw new RuntimeException("The browser specified in the properties file is not valid.");
		}
	}
	
	
	@AfterMethod(alwaysRun = true)
	public void closeDriver() {
		driverThreadLocal.get().quit();
	}
	
}
