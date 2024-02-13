package framework.appium.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.google.common.collect.ImmutableMap;

import framework.core.WebDriverFactory;
import framework.testNG.TestNGUtils;
import framework.utils.AppiumPropperties;
import framework.utils.TestProperties;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
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
		URL appiumServerURL=new URL("http://"+appiumServerHost+":"+appiumServerPort);
		System.out.println(appiumServerURL);
		if (TestProperties.APPLICATION_URL.toString().contains(".exe")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("appium:automationName", AppiumPropperties.WINDOWS_AUTOMATION_NAME.toString());
			capabilities.setCapability("platformName", AppiumPropperties.WINDOWS_PLATFORM_NAME.toString());
			capabilities.setCapability("appium:deviceName", AppiumPropperties.WINDOWS_DEVICE_NAME.toString());
			capabilities.setCapability("appium:app", TestProperties.APPLICATION_URL.toString());
			driverThreadLocal.set(new WindowsDriver(appiumServerURL, capabilities));
		}else if (TestProperties.APPLICATION_URL.toString().contains(".apk")) {
			// instead of desired capabilities u can use UiAutomator2Options also, both are valid
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("appium:automationName", AppiumPropperties.MOBILE_ANDROID_AUTOMATION_NAME.toString());
			capabilities.setCapability("platformName", AppiumPropperties.MOBILE_ANDROID_PLATFORM_NAME.toString());
			capabilities.setCapability("appium:deviceName", AppiumPropperties.MOBILE_ANDROID_DEVICE_NAME.toString());
			capabilities.setCapability("appium:udid", AppiumPropperties.MOBILE_ANDROID_DEVICE_UDID.toString());
			// if app is already installed in device then add app package and activity and comment out app, install timeout and avd and avdLaunchtiimeout capabilities
			capabilities.setCapability("appium:appPackage", AppiumPropperties.MOBILE_ANDROID_APP_PACKAGE_NAME.toString());
			capabilities.setCapability("appium:appActivity", AppiumPropperties.MOBILE_ANDROID_APP_ACTIVITY_NAME.toString());
//			capabilities.setCapability("appium:app", TestProperties.APPLICATION_URL.toString());
//			capabilities.setCapability("appium:androidInstallTimeout", 300000);  //(time in milliseconds, we given 3 mins)
//			if (AppiumPropperties.MOBILE_DEVICE_TYPE.toString().equalsIgnoreCase(AppiumPropperties.MOBILE_VIRTUAL_DEVICE.toString().toString())) {
//				capabilities.setCapability("appium:avd", AppiumPropperties.MOBILE_ANDROID_DEVICE_NAME.toString());
//				capabilities.setCapability("appium:avdLaunchTimeout", 300000);
//			}
			driverThreadLocal.set(new AndroidDriver(appiumServerURL, capabilities));
		}else if (TestProperties.APPLICATION_URL.toString().contains(".ipa") || TestProperties.APPLICATION_URL.toString().contains(".app")) {
			// note that  .ipa apps cannot be installed on simulators. only .app apps u can.
			// instead of desired capabilities u can use UiAutomator2Options also, both are valid
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("appium:automationName", AppiumPropperties.MOBILE_IOS_AUTOMATION_NAME.toString());
			capabilities.setCapability("platformName", AppiumPropperties.MOBILE_IOS_PLATFORM_NAME.toString());
			capabilities.setCapability("appium:deviceName", AppiumPropperties.MOBILE_IOS_DEVICE_NAME.toString());
			// if app is already installed in device(and u dont want to install via code) then add app package and activity and comment out app capability
			capabilities.setCapability("appium:udid", AppiumPropperties.MOBILE_IOS_DEVICE_UDID.toString());
			//capabilities.setCapability("appium:bundleId", AppiumPropperties.MOBILE_IOS_APP_BUNDLE_ID.toString());
			capabilities.setCapability("appium:app", TestProperties.APPLICATION_URL.toString());
			if (AppiumPropperties.MOBILE_DEVICE_TYPE.toString().equalsIgnoreCase(AppiumPropperties.MOBILE_VIRTUAL_DEVICE.toString().toString())) {
				capabilities.setCapability("appium:simulatorStartupTimeout", 300000);
			}
			driverThreadLocal.set(new IOSDriver(appiumServerURL, capabilities));
		}else {
			throw new RuntimeException("Application Type/Test URL specified in test.priperties is file is not valid");
		}
		// setting appiumdriver in webdriver in webdriver factory class, so webdriver action methods can be reused for webview in appium
		WebDriverFactory.setDriver(getDriver());
	}
	
	
	@AfterMethod(alwaysRun=true)
	public void closeDriver() {
		TestNGUtils.reportLog("Close the application");
		//driverThreadLocal.get().findElement(AppiumBy.name("close")).click();
	}
		
	
}
