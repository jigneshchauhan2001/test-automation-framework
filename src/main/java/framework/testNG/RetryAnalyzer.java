package framework.testNG;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.reporters.AbstractXmlReporter.Count;

import framework.utils.TestProperties;

public class RetryAnalyzer implements IRetryAnalyzer {

	private int count=0;
	private int maxCount=TestProperties.TESTCASE_RETRY_COUNT.toInteger();
	@Override
	public boolean retry(ITestResult result) {
		// below line formats like this --> if result.getMethod().getMethodName() is "LoginMethod"--> then it will do format like this and will print
		// [LoginMethod] [FAILED]
		System.err.println(String.format("[%s]\t\t[FAILED]", result.getMethod().getMethodName()));
		
		// retry logic
		String failCount = null;
		if (count < maxCount) {
			count++;
			failCount=""+count;
			TestNGUtils.reportLog("Testcase: "+result.getMethod().getMethodName()+ " ,Count: "+failCount);
			System.err.println(String.format("[%s]\t\t[RETRY]", result.getMethod().getMethodName()));
			return true;
		}
		return false;
		
		
		
	}

}
