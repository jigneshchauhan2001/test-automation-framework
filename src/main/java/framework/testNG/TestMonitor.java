package framework.testNG;


import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IExecutionListener;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import framework.appium.core.AppiumDriverFactory;
import framework.core.ExecutionSetup;
import framework.core.WebDriverFactory;
import io.qameta.allure.Allure;

public class TestMonitor  extends TestListenerAdapter implements IHookable,IExecutionListener{

	// Ihookable used to control test behaviour at runtime so you can configure this interface to take screenshot when any exception thrown, 
	// as well if you want to skip certain testcase based on certain condition that is also possible using Ihookable.
	// if u want to implement this logic inside onTestFailure  method coming from TestListerAdapter you can do that as well.

	
	
	// just like before suite
	@Override
	public void onExecutionFinish() {
		ExecutionSetup.stopServerExecution();
	}
	
	// just like after suite
	@Override
	public void onExecutionStart() {
		ExecutionSetup.startServerExecution();
	}
	
	// just like before test
	@Override
	public void onStart(ITestContext testContext) {
		
	}
	
	// just like after test
	@Override
	public void onFinish(ITestContext testContext) {
		
	}
	
	
	// just like before method
	@Override
	public void onTestStart(ITestResult testResult) {
	}
	
	
	@Override
	public void onTestFailure(ITestResult testResult) {
		
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult testResult) {
		
	}
	
	@Override
	public void onTestSkipped(ITestResult testResult) {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void screenshotOnFailure(String testcaseName) {
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MMM-dd-HH-mm-ss");
		Calendar calendar=Calendar.getInstance();
		String testFailureTime=dateFormat.format(calendar.getTime());
		String screenshotLocation ="./target/testNG/screenshot/"+testcaseName+"_"+testFailureTime+".PNG";
		File destination=new File(screenshotLocation);
		File source=null;
		
		if (WebDriverFactory.getDriver() != null) {
			// generating screeshots under screenshot folder in testNG folder in target folder.
			try {
				source =((TakesScreenshot)WebDriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(source, destination);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (AppiumDriverFactory.getDriver()!= null) {
			try {
				source =((TakesScreenshot)AppiumDriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(source, destination);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// attaching screenshots in TestNG and allure reports
		try {
			TestNGUtils.reportLog("<a href=" + "../screenshot/"+testcaseName+"_"+testFailureTime+".PNG" + " target=_blank>SCREENSHOT</a>");
			Allure.addAttachment(testcaseName, new FileInputStream(destination));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run(IHookCallBack callBack, ITestResult testResult) {
		callBack.runTestMethod(testResult);
		if (testResult.getThrowable()!=null) {
			screenshotOnFailure(testResult.getMethod().getMethodName());
		}
		
	}
	
	
	
}
