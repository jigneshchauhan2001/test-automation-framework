package framework.testNG;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.testng.Reporter;

import io.qameta.allure.Allure;

public class TestNGUtils {

	/** 
	 * this method used to generate steps in testng and allure report.
	 * @param message that you want to print in report
	 */
	public static void reportLog(String message) {
		// adding all messages as test-steps in allure
		Allure.step(message);
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
		Calendar calendar = Calendar.getInstance();
		String timeStamp = dateFormat.format(calendar.getTime());
		message=timeStamp+ " : "+message;
		Reporter.log(message);
	}
}
