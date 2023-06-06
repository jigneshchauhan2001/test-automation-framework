package framework.testNG;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import framework.utils.TestProperties;
import rms.testData.excel.RMSProperties;

public class Trigger {

	public static void main(String[] args) {
	
		// loadig properties file
		try {
			TestProperties.loadProperties("test.properties");
			RMSProperties.loadProperties("test-data\\RMS_Data\\rms.properties");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		XmlSuite suite = new XmlSuite();
		suite.setParallel(XmlSuite.ParallelMode.METHODS);
		suite.setThreadCount((int)TestProperties.TESTNG_THREAD_COUNT.toInteger());
		suite.setDataProviderThreadCount((int)TestProperties.TESTNG_DATAPROVIDER_THREAD_COUNT.toInteger());
		
		List<XmlPackage> testPackages = new ArrayList<XmlPackage>();
		String[] packages = TestProperties.TESTNG_TESTCASE_PACKAGE.toString().split(",");
		for (int i = 0; i < packages.length; i++) {
			testPackages.add(new XmlPackage(packages[i]));
		}
		XmlTest test = new XmlTest(suite);
		test.setPackages(testPackages);
		
		String[] testGroupsIncluded = TestProperties.TESTNG_GROUP_INCLUDE.toString().split(",");
		for (int i = 0; i < testGroupsIncluded.length; i++) {
			test.addIncludedGroup(testGroupsIncluded[i]);
		}
		
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		
		TestNG testNG = new TestNG();
		testNG.setOutputDirectory(new StringBuilder().append("./target/testNG/test-output").toString());
		testNG.setXmlSuites(suites);
		// adding listeners 
		TestMonitor testMonitor=new TestMonitor();
		testNG.addListener((ITestNGListener)testMonitor);
		
		//testNG.setDataProviderThreadCount((int)(int)TestProperties.TESTNG_DATAPROVIDER_THREAD_COUNT.toInteger());
		
		// add code for listerns and allre reports when you add
		
		testNG.run();
		System.exit(0);
	}
	
	
	// create allure report method here --execueteAllure
}
