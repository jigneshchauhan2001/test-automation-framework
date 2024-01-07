package framework.testNG;

import java.util.ArrayList;
import java.util.List;

import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import framework.core.ExecutionSetup;
import framework.utils.TestProperties;

public class Trigger {

	// PROJECT EXECUTION FLOW --> main --> before method--> test--> after method. 
	// Here we cannot use beforeTest or beforeSuite because in pom.xml we setup mainClass as trigger so it need to have main method
	// so we cannot eliminate main method and cannot use beforeTest or beforeSuite, hence divided execution flow into methods.
	public static void main(String[] args) {		
		// loadig properties file
		TestProperties.loadProperties("test.properties");
		// start grid locally or on docker as per property value
		ExecutionSetup.startGridExecution();
		// start test execution
		ExecutionSetup.startTestExecution();
		ExecutionSetup.stopGridExecution();
	}
}
