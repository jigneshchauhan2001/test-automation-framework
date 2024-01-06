package framework.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public enum TestProperties {

	// u can provide null value here as we are reading value from properties file so what value you define there that it will be replaced here
	APPLICATION_URL("test.url", null, Type.String), 
	TESTNG_THREAD_COUNT("testng.thread.count", null, Type.Integer),
	TESTNG_DATAPROVIDER_THREAD_COUNT("testng.dataProvider.thread.count", null, Type.Integer),
	TESTNG_TESTCASE_PACKAGE("testng.testcase.package", null, Type.String),
	TESTNG_GROUP_INCLUDE("testng.group.include", null,Type.String),
	TEST_BROWSER("test.browser", null, Type.String),
	SELENIUM_HOST("selenium.host", null, Type.String),
	SELENIUM_HUB_PORT("selenium.hub.port", null, Type.String),
	MOBILE_EXECUTUION("mobile.execution", null, Type.String),
	TEST_TIMEOUT("test.timeout", null, Type.Integer),
	TESTCASE_RETRY_COUNT("testcase.retry.count", null,Type.Integer),

	// Appium Properties
	APPIUM_PLATFORM_NAME("appium.platform.name",null,Type.String),
	APPIUM_DEVICE_NAME("appium.device.name",null,Type.String),
	APPIUM_APP_NAME("appium.app.name",null,Type.String),
	APPIUM_APP_PACKAGE_NAME("appium.appPackage.name",null,Type.String),
	APPIUM_APP_ACTIVITY_NAME("appium.appActivity.name",null,Type.String), 
	APPIUM_SERVER_COUNT("appium.server.count", null,Type.Integer),
	APPIUM_SERVER1_HOST("appium.server1.host", null,Type.String),
	APPIUM_SERVER2_HOST("appium.server2.host", null,Type.String),
	APPIUM_SERVER1_PORT("appium.server1.port", null,Type.String),
	APPIUM_SERVER2_PORT("appium.server2.port", null,Type.String);
	
	
	private String key;
	private Object value;
	private Type type;

	private enum Type {
		Integer, String, Boolean, Long
	};

	private TestProperties(String key, Object value, Type type) {
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

	public static void loadProperties(String testPropertyFile) throws FileNotFoundException, IOException {
		Properties p = new Properties();
		p.load(new FileInputStream(testPropertyFile));

		for (TestProperties testProperty : TestProperties.values()) {
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
