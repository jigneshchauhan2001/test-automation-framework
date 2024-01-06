package framework.testNG;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.testng.Reporter;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class TestNGUtils {

	public enum LogType{
		STEP,SUBSTEP,VERIFICATION_STEP,INNER_VERIFICATION_STEP,ERROR_MESSAGE,INNER_ERROR_MESSAGE;
	}
	
	public static String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
		Calendar calendar = Calendar.getInstance();
		String timeStamp = dateFormat.format(calendar.getTime());
		return timeStamp;
	}
		
	@Step("{message}")
	public static void reportLog(String message,LogType logType) {
		switch (logType) {
		case STEP:
			message=step(message);
			break;
		case SUBSTEP:
			message=subStep(message);
			break;
		case VERIFICATION_STEP:
			message=verificationStep(message);
			break;
		case INNER_VERIFICATION_STEP:
			message=innerVerificationStep(message);
			break;
		case ERROR_MESSAGE:
			message=errorMessage(message);
			break;
		case INNER_ERROR_MESSAGE:
			message=innerErrorMessage(message);
			break;
		default:
			break;
		}
	}

	
	
	private static String innerErrorMessage(String message) {
	    message = "<font face=\"Calibri, sans-serif\" size=\"2\" color=\"red\">" + "&bull; " + "<b>" + message + "</b>" + "</font>";
	    Reporter.log(getCurrentTime() + " : " + message);
	    return message;
	}

	private static String errorMessage(String message) {
	    message = "<font face=\"Calibri, sans-serif\" color=\"red\"><b>" + message + "</b></font>";
	    Reporter.log(getCurrentTime() + " : " + message);
	    return message;
	}

	private static String innerVerificationStep(String message) {
	    message = "<font face=\"Calibri, sans-serif\" size=\"2\" color=\"green\">" + "&bull; " + "<b>" + message + "</b>" + "</font>";
	    Reporter.log(getCurrentTime() + " : " + message);
	    return message;
	}

	private static String verificationStep(String message) {
	    message = "<font face=\"Calibri, sans-serif\" color=\"green\"><b>" + message + "</b></font>";
	    Reporter.log(getCurrentTime() + " : " + message);
	    return message;
	}

	private static String subStep(String message) {
	    message = "<font face=\"Calibri, sans-serif\" size=\"2\">&nbsp;&cir;&nbsp;" + message + "</font>";
	    Reporter.log(getCurrentTime() + " : " + message);
	    return message;
	}

	private static String step(String message) {
	    message = "&nbsp;&bull;&nbsp;<font face=\"Calibri, sans-serif\">" + message + "</font>";
	    Reporter.log(getCurrentTime() + " : " + message);
	    return message;
	}

	/** 
	 * this method used to generate steps in testng and allure report.
	 * @param message that you want to print in report
	 */
	@Step("{message}")
	public static void reportLog(String message) {
	    message = "<font face=\"Calibri, sans-serif\">" + message + "</font>";
	    Allure.step(message);
	    Reporter.log(getCurrentTime() + " : " + message);
	}

	
	
	
	
}
