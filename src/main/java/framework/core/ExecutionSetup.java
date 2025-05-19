package framework.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import framework.testNG.TestMonitor;
import framework.utils.AppiumPropperties;
import framework.utils.TestProperties;

public class ExecutionSetup {

	/**
	 * @desc starts selenium grid or appium server execution either locally or on docker
	 */
	public static void startServerExecution() {
		if (TestProperties.TEST_EXECUTION_ENVRIONMENT.toString().equalsIgnoreCase(ExecutionEnvironment.SELENIUM_DOCKER)) {
			startGridDockerExecution();
		} else if (TestProperties.TEST_EXECUTION_ENVRIONMENT.toString().equalsIgnoreCase(ExecutionEnvironment.SELENIUM_LOCAL)) {
			startGridLocalExecution();
		}
		else if(TestProperties.TEST_EXECUTION_ENVRIONMENT.toString().equalsIgnoreCase(ExecutionEnvironment.APPIUM_DOCKER)){
			
		}else if(TestProperties.TEST_EXECUTION_ENVRIONMENT.toString().equalsIgnoreCase(ExecutionEnvironment.APPIUM_LOCAL)){
			//startAppiumLocalExecution();
		}
		else {
			throw new RuntimeException("Test execution type not defined in test.properties file");
		}
	}

	
	/**
	 * @desc stops selenium grid or appium server execution either locally or on docker
	 */
	public static void stopServerExecution() {
		if (TestProperties.TEST_EXECUTION_ENVRIONMENT.toString().equalsIgnoreCase(ExecutionEnvironment.SELENIUM_DOCKER)) {
			stopGridDockerExecution();
		} else if (TestProperties.TEST_EXECUTION_ENVRIONMENT.toString().equalsIgnoreCase(ExecutionEnvironment.SELENIUM_LOCAL)) {
			stopGridLocalExecution();
		}
		else if(TestProperties.TEST_EXECUTION_ENVRIONMENT.toString().equalsIgnoreCase(ExecutionEnvironment.APPIUM_DOCKER)){
			
		}else if(TestProperties.TEST_EXECUTION_ENVRIONMENT.toString().equalsIgnoreCase(ExecutionEnvironment.APPIUM_LOCAL)){
			//stopAppiumLocalExecution();
		}else {
			throw new RuntimeException("Test execution type not defined in test.properties file");
		}
		System.exit(0);
	}

	/**
	 * @desc starts grid execution on docker, but before that make sure docker
	 *       desktop is started manuallly
	 */
	public static void startGridDockerExecution() {
		try {
			Process process = Runtime.getRuntime().exec("start_selenium_docker_execution.bat");
			// Capture and print only lines with the BATCH_OUTPUT: prefix
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().startsWith("BATCH_OUTPUT:")) {
					System.out.println(line.trim().substring("BATCH_OUTPUT:".length()));
				}
			}
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("Unable to start selenium grid docker containers");
		}
	}

	/**
	 * @desc stops grid execution on docker, and after that if you want you can
	 *       stops docker desktop manually and also you can kill all instances from
	 *       task manager related to docker
	 */
	public static void stopGridDockerExecution() {
		try {
			Process process = Runtime.getRuntime().exec("stop_selenium_docker_execution.bat");
			// Capture and print only lines with the BATCH_OUTPUT: prefix
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().startsWith("BATCH_OUTPUT:")) {
					System.out.println(line.trim().substring("BATCH_OUTPUT:".length()));
				}
			}
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("Unable to start selenium grid docker containers");
		}
	}

	/**
	 * @desc starts grid execution locally, IMP: Hard Wait is given, if found wait for dynamic wait, then replace it
	 * 
	 */

	public static void startGridLocalExecution() {
		try {
			Process process = Runtime.getRuntime().exec("start_selenium_local_execution.bat");
			// Hard wait
			System.out.println("Starting Hub on port 4444 and node on port 4444 in local host");
			Thread.sleep(10000);
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException("Unable to start selenium grid in local environment");
		}
	}

	/**
	 * @desc stops grid execution locally
	 */
	public static void stopGridLocalExecution() {
		try {
			Process process = Runtime.getRuntime().exec("stop_selenium_local_execution.bat");
			System.out.println("Removing  Grid Hub and node from local host");
		} catch (IOException e) {
			throw new RuntimeException("Unable to stop selenium grid in local environment");
		}
	}
	
	
	
	/**
	 * @desc this method starts appium server on port 7500
	 */
	public static void startAppiumLocalExecution() {
		try {
			Process process = Runtime.getRuntime().exec("start_appium_local_execution.bat");
			System.out.println("Starting Appium Server on port 7500 in local host");
			// Hard wait
			Thread.sleep(10000);
		} catch (IOException | InterruptedException  e) {
			throw new RuntimeException("Unable to start appium server in local environment");
		}
		
	}
	
	/**
	 * @desc stops appium server locally
	 */
	public static void stopAppiumLocalExecution() {
		try {
			Process process = Runtime.getRuntime().exec("stop_appium_local_execution.bat");
			System.out.println("Removing  Appium server from local host");
			Thread.sleep(5000);
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException("Unable to stop appium server in local environment");
		}
	}
	

	/**
	 * @desc starts test execution
	 */
	public static void setupTestExecution() {
		XmlSuite suite = new XmlSuite();
		suite.setParallel(XmlSuite.ParallelMode.METHODS);
		suite.setThreadCount((int) TestProperties.TESTNG_THREAD_COUNT.toInteger());
		suite.setDataProviderThreadCount((int) TestProperties.TESTNG_DATAPROVIDER_THREAD_COUNT.toInteger());
		List<XmlPackage> testPackages = new ArrayList<XmlPackage>();
		String[] packages = TestProperties.TESTNG_TESTCASE_PACKAGE.toString().split(",");
		for (int i = 0; i < packages.length; i++) {
			testPackages.add(new XmlPackage(packages[i]));
		}

		if (TestProperties.APPLICATION_URL.toString().contains(".exe")
				|| TestProperties.APPLICATION_URL.toString().contains(".apk")
				|| TestProperties.APPLICATION_URL.toString().contains(".ipa")) {
			for (int i = 1; i <= (int) AppiumPropperties.APPIUM_SERVER_COUNT.getProperty(); i++) {
				XmlTest test = new XmlTest(suite);
				test.setPackages(testPackages);
				test.addIncludedGroup(TestProperties.TESTNG_GROUP_INCLUDE.toString());
				String[] includedGroups = TestProperties.TESTNG_GROUP_INCLUDE.toString().split(",");
				for (int j = 0; j <= includedGroups.length - 1; j++) {
					test.addIncludedGroup(includedGroups[j]);
				}
				test.addParameter("appiumServerHost",
						(String) AppiumPropperties.valueOf("APPIUM_SERVER" + i + "_HOST").getProperty());
				test.addParameter("appiumServerPort",
						(String) AppiumPropperties.valueOf("APPIUM_SERVER" + i + "_PORT").getProperty());
				test.setName("Test-" + i);
			}
		} else {
			XmlTest test = new XmlTest(suite);
			test.setPackages(testPackages);
			test.addIncludedGroup(TestProperties.TESTNG_GROUP_INCLUDE.toString());
			String[] includedGroups = TestProperties.TESTNG_GROUP_INCLUDE.toString().split(",");
			for (int j = 0; j <= includedGroups.length - 1; j++) {
				test.addIncludedGroup(includedGroups[j]);
			}
		}
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		TestNG testNG = new TestNG();
		testNG.setOutputDirectory(new StringBuilder().append("./target/testNG/test-output").toString());
		testNG.setXmlSuites(suites);
		// adding listeners
		TestMonitor testMonitor = new TestMonitor();
		testNG.addListener((ITestNGListener) testMonitor);

		testNG.setDataProviderThreadCount((int) TestProperties.TESTNG_DATAPROVIDER_THREAD_COUNT.toInteger());
		testNG.run();
	}

}
