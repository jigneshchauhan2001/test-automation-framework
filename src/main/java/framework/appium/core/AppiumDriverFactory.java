package framework.appium.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import framework.testNG.TestNGUtils;
import framework.utils.TestProperties;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.windows.WindowsDriver;

public class AppiumDriverFactory {

	private static ThreadLocal<AppiumDriver> driverThreadLocal = new ThreadLocal<AppiumDriver>();
	private static ThreadLocal<HashMap<String, Object>> hashMapLocal = new ThreadLocal<HashMap<String,Object>>() {
		@Override
		protected HashMap<String, Object> initialValue(){
			return new HashMap<>();
		}
	};
	
	public static AppiumDriver getDriver() { 
		return driverThreadLocal.get();
	}
	
	public static Action getAction() {
		return new Action(driverThreadLocal.get(), hashMapLocal.get());
	}
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({"appiumServerHost","appiumServerPort"})
	public void assignDriver(String appiumServerHost,String appiumServerPort) throws MalformedURLException {
		URL appiumServerURL=new URL("http://"+appiumServerHost+":"+appiumServerPort+"/wd/hub");
		if (TestProperties.APPLICATION_URL.toString().contains(".exe")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("platformName", TestProperties.APPIUM_PLATFORM_NAME.toString());
			capabilities.setCapability("deviceName", TestProperties.APPIUM_DEVICE_NAME.toString());
			capabilities.setCapability("app", TestProperties.APPLICATION_URL.toString());
			driverThreadLocal.set(new WindowsDriver(appiumServerURL, capabilities));
		}else if (TestProperties.APPLICATION_URL.toString().contains(".apk")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, TestProperties.APPIUM_PLATFORM_NAME.toString());
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, TestProperties.APPIUM_DEVICE_NAME.toString());
			capabilities.setCapability(MobileCapabilityType.APP, TestProperties.APPIUM_APP_NAME.toString());
			capabilities.setCapability("appPackage", TestProperties.APPIUM_APP_PACKAGE_NAME.toString());
			capabilities.setCapability("appActivity", TestProperties.APPIUM_APP_ACTIVITY_NAME.toString());
			driverThreadLocal.set(new AndroidDriver(appiumServerURL, capabilities));
		}else if (TestProperties.APPLICATION_URL.toString().contains(".ipa")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			// add iphone app capability
			driverThreadLocal.set(new IOSDriver(appiumServerURL, capabilities));
		}else {
			throw new RuntimeException("Application type specified in test.priperties is file is not valid");
		}		
	}
	
	
	@AfterMethod(alwaysRun=true)
	public void closeDriver() {
		TestNGUtils.reportLog("Click on close button to close notepad");
		driverThreadLocal.get().findElement(AppiumBy.name("close")).click();
	}
	
	
	
	
}
