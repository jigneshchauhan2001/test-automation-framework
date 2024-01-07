package framework.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import framework.testNG.TestMonitor;
import framework.utils.TestProperties;

public class ExecutionSetup {
	
		public static void startGridExecution() {
			if (TestProperties.TEST_EXECUTION_ENVRIONMENT.toString().equalsIgnoreCase(ExecutionEnvironment.DOCKER)) {
				startDockerExecution();
			}
			else if(TestProperties.TEST_EXECUTION_ENVRIONMENT.toString().equalsIgnoreCase(ExecutionEnvironment.LOCAL)){
				startLocalExecution();
			}
			else {
				throw new RuntimeException("Test execution type not defined in test.properties file");
			}
		}
		
		
		public static void stopGridExecution() {
			if (TestProperties.TEST_EXECUTION_ENVRIONMENT.toString().equalsIgnoreCase(ExecutionEnvironment.DOCKER)) {
				stopDockerExecution();
			}
			else if(TestProperties.TEST_EXECUTION_ENVRIONMENT.toString().equalsIgnoreCase(ExecutionEnvironment.LOCAL)){
				stopLocalExecution();
			}
			else {
				throw new RuntimeException("Test execution type not defined in test.properties file");
			}
		}

		
		
		public static void startTestExecution() {
			XmlSuite suite = new XmlSuite();
			suite.setParallel(XmlSuite.ParallelMode.METHODS);
			suite.setThreadCount((int)TestProperties.TESTNG_THREAD_COUNT.toInteger());
			suite.setDataProviderThreadCount((int)TestProperties.TESTNG_DATAPROVIDER_THREAD_COUNT.toInteger());
			List<XmlPackage> testPackages = new ArrayList<XmlPackage>();
			String[] packages = TestProperties.TESTNG_TESTCASE_PACKAGE.toString().split(",");
			for (int i = 0; i < packages.length; i++) {
				testPackages.add(new XmlPackage(packages[i]));
			}
		
			if (TestProperties.APPLICATION_URL.toString().contains(".exe") || TestProperties.APPLICATION_URL.toString().contains(".apk") || TestProperties.APPLICATION_URL.toString().contains(".ipa")) {
				for (int i = 1; i <=(int)TestProperties.APPIUM_SERVER_COUNT.getProperty(); i++) {
					XmlTest test=new XmlTest(suite);
					test.setPackages(testPackages);
					test.addIncludedGroup(TestProperties.TESTNG_GROUP_INCLUDE.toString());
					String[] includedGroups=TestProperties.TESTNG_GROUP_INCLUDE.toString().split(",");
					for (int j = 0; j <= includedGroups.length-1; j++) {
						test.addIncludedGroup(includedGroups[j]);
					}
					test.addParameter("appiumServerHost", (String)TestProperties.valueOf("APPIUM_SERVER"+i+"_HOST").getProperty());
					test.addParameter("appiumServerPort", (String)TestProperties.valueOf("APPIUM_SERVER"+i+"_PORT").getProperty());
					test.setName("Test-"+i);
				}
			}else {
				XmlTest test=new XmlTest(suite);
				test.setPackages(testPackages);
				test.addIncludedGroup(TestProperties.TESTNG_GROUP_INCLUDE.toString());
				String[] includedGroups=TestProperties.TESTNG_GROUP_INCLUDE.toString().split(",");
				for (int j = 0; j <= includedGroups.length-1; j++) {
					test.addIncludedGroup(includedGroups[j]);
				}
			}
			List<XmlSuite> suites = new ArrayList<XmlSuite>();
			suites.add(suite);
			TestNG testNG = new TestNG();
			testNG.setOutputDirectory(new StringBuilder().append("./target/testNG/test-output").toString());
			testNG.setXmlSuites(suites);
			// adding listeners 
			TestMonitor testMonitor=new TestMonitor();
			testNG.addListener((ITestNGListener)testMonitor);
			
			testNG.setDataProviderThreadCount((int)(int)TestProperties.TESTNG_DATAPROVIDER_THREAD_COUNT.toInteger());
			testNG.run();
			System.exit(0);
		
		}
		
		
		public static void startDockerExecution() {
			try {
				Process process=Runtime.getRuntime().exec("cmd /c start_docker_execution.bat");
	        // process.waitFor();
				Thread.sleep(20000);
			} catch (IOException | InterruptedException e) {
				throw new RuntimeException("Unable to start selenium grid docker containers");
			}
		}

		
		public static void stopDockerExecution() {
			try {
				Process process=Runtime.getRuntime().exec("cmd /c stop_docker_execution.bat");
				//process.waitFor();
				Thread.sleep(20000);
			} catch (IOException | InterruptedException e) {
				throw new RuntimeException("Unable to stop selenium grid docker containers");
			}
		}

		public static void startLocalExecution() {
			try {
				Process process=Runtime.getRuntime().exec("cmd /c start_local_execution.bat");
				//process.waitFor();
				Thread.sleep(20000);
			} catch (IOException | InterruptedException e) {
				throw new RuntimeException("Unable to start selenium grid in local environment");
			}
		}
		
		public static void stopLocalExecution() {
			try {
				Process process=Runtime.getRuntime().exec("cmd /c stop_local_execution.bat");
				//process.waitFor();
			} catch (IOException e) {
				throw new RuntimeException("Unable to stop selenium grid in local environment");
			}
		}
		

	

}
