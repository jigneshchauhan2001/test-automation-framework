package framework.testNG;

import java.util.ArrayList;
import java.util.List;

import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import framework.core.ExecutionSetup;
import framework.utils.AppiumPropperties;
import framework.utils.TestProperties;

public class Trigger {

		// in sure fire plugin either we have to pass main method class name or either we have to pass xml file  name to start executing tests, so we go by main method
		public static void main(String[] args) {	
		// loadig properties file
		TestProperties.loadProperties("test.properties");
		AppiumPropperties.loadProperties("appium.properties");
		// start test execution
		ExecutionSetup.setupTestExecution();
	}
}
