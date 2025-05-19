package framework.utils;

import java.io.FileInputStream;
import java.util.Properties;


public enum AppiumPropperties {

	
	// Appium Properties
	// common
	APPIUM_SERVER_COUNT("appium.server.count", null,Type.Integer),
	APPIUM_SERVER1_HOST("appium.server1.host", null,Type.String),
	APPIUM_SERVER2_HOST("appium.server2.host", null,Type.String),
	APPIUM_SERVER1_PORT("appium.server1.port", null,Type.String),
	APPIUM_SERVER2_PORT("appium.server2.port", null,Type.String),
	MOBILE_DEVICE_TYPE("mobile.device.type", null,Type.String),  // real or virtual
	MOBILE_REAL_DEVICE("mobile.device.real", "real",Type.String),
	MOBILE_VIRTUAL_DEVICE("mobile.device.virtual", "virtual",Type.String),
	MOBILE_UNLOCK_TYPE("mobile.unlock.type", null, Type.String),
	MOBILE_UNLOCK_KEY("mobile.unlock.key", null, Type.String),
	
	// andorid-mobile(virtual or real just change UDID(find from adb devices command) and device name, all other will be common)
	MOBILE_ANDROID_AUTOMATION_NAME("mobile.android.automation.name",null,Type.String),
	MOBILE_ANDROID_PLATFORM_NAME("mobile.android.platform.name",null,Type.String),
	MOBILE_ANDROID_DEVICE_NAME("mobile.android.device.name",null,Type.String),
	MOBILE_ANDROID_DEVICE_UDID("mobile.android.device.udid",null,Type.String),
	MOBILE_ANDROID_APP_PACKAGE_NAME("mobile.android.appPackage.name",null,Type.String),
	MOBILE_ANDROID_APP_ACTIVITY_NAME("mobile.android.appActivity.name",null,Type.String),
	
	// ios-mobile (virtual or real just change UDID and device name)
	MOBILE_IOS_AUTOMATION_NAME("mobile.ios.automation.name",null,Type.String),
	MOBILE_IOS_PLATFORM_NAME("mobile.ios.platform.name",null,Type.String),
	MOBILE_IOS_DEVICE_NAME("mobile.ios.device.name",null,Type.String),
	MOBILE_IOS_DEVICE_UDID("mobile.ios.device.udid",null,Type.String),
	MOBILE_IOS_APP_BUNDLE_ID("mobile.ios.bundleId",null,Type.String),
	
	
	// windows
	WINDOWS_AUTOMATION_NAME("windows.automation.name",null,Type.String),
	WINDOWS_PLATFORM_NAME("windows.platform.name",null,Type.String),
	WINDOWS_DEVICE_NAME("windows.device.name",null,Type.String);

		
	private String key;
	private Object value;
	private Type type;

	private enum Type {
		Integer, String, Boolean, Long
	};

	private AppiumPropperties(String key, Object value, Type type) {
		this.key = key;
		this.value = value;
		this.type = type;
	}

	public void setProperty(Object value) {
		this.value = value;
	}

	public Object getProperty() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.value.toString();
	}

	public Integer toInteger() {
		return Integer.parseInt(this.value.toString());
	}

	public Long toLong() {
		return Long.parseLong(this.value.toString());
	}

	public Boolean toBoolean() {
		return Boolean.parseBoolean(this.value.toString());
	}

	public static void loadProperties(String testPropertyFile) {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(testPropertyFile));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to load property file : "+testPropertyFile+ " error message : "+e.getMessage());
		} 

		for (AppiumPropperties testProperty : AppiumPropperties.values()) {
			String valFromFile = p.getProperty(testProperty.key);

			if (valFromFile != null) {
				if (testProperty.type == Type.Integer) {
					testProperty.setProperty(Integer.parseInt(valFromFile));
				} else if (testProperty.type == Type.Long) {
					testProperty.setProperty(Long.parseLong(valFromFile));
				} else if (testProperty.type == Type.Boolean) {
					testProperty.setProperty(Boolean.parseBoolean(valFromFile));
				} else {
					testProperty.setProperty(valFromFile);
				}

			}

		}
	}
}
