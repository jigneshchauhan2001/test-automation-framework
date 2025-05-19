package ecommerce.testcases;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.testng.annotations.Test;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;

import ecommerce.entities.Start;
import ecommerce.testData.yaml.LoginPageTestData;
import framework.core.Element;
import framework.core.WebDriverFactory;
import framework.testNG.RetryAnalyzer;
import framework.testNG.TestGroups;
import framework.testNG.TestNGUtils;
import framework.testNG.TestNGUtils.LogType;
import framework.utils.SFTPUtility;
import framework.utils.SFTPproperties;

public class LoginPageTestCases extends WebDriverFactory {

	@Test(retryAnalyzer = RetryAnalyzer.class,groups = {TestGroups.DESKTOP_REGRESSION,TestGroups.MOBILE_REGRESSION})
	public void LoginFirstTest(){
		String testcaseName=new Exception().getStackTrace()[0].getMethodName();
		LoginPageTestData testData=LoginPageTestData.fetch(testcaseName);
		Start.
		asGuestUser
		.navigateToHomePage()
		._inHomePage()
		.fetchOrderNumber(testData);
		
		// update yaml
		LoginPageTestData.updateTestDataYaml(testcaseName, testData);	
		
		// you can also modify some string coming from yaml 
		String name=testData.itemDetails.modifyString.replace("Jignesh", "Sunil");
		System.out.println(name);
		
	}
	
	
	@Test(retryAnalyzer = RetryAnalyzer.class,groups = {TestGroups.DESKTOP_REGRESSION})
	public void logExample(){
		// multiple formats example--till 9 u can use it.
		Element element = new Element("Element", "//div[text()={0}]//div[{1}]", Element.XPATH);
		String f1="f1";
		String f2="f2";
		System.out.println(element.format(f1,f2).getElementValue());;  /// op: //div[text()={f1}]//div[{f2}]
		//single format example
		Element ele = new Element("Element", "//div[text()={0}]", Element.XPATH);
		String f3="f3";
		System.out.println(ele.format(f3).getElementValue());;  /// op: //div[text()={f3}]
					
		
		TestNGUtils.reportLog("Hello I am regular log");
		TestNGUtils.reportLog("Hello I am step", LogType.STEP);
		TestNGUtils.reportLog("Hello I am substep", LogType.SUBSTEP);
		TestNGUtils.reportLog("Hello I am verfication step", LogType.VERIFICATION_STEP);
		TestNGUtils.reportLog("Hello I am inner verification step", LogType.INNER_VERIFICATION_STEP);
		TestNGUtils.reportLog("Hello I am error message", LogType.ERROR_MESSAGE);
		TestNGUtils.reportLog("Hello I am inner error message", LogType.INNER_ERROR_MESSAGE);
		
	}
	
	
	
	
	@Test(retryAnalyzer = RetryAnalyzer.class, groups = {"SFTPTest"})
	public static void downloadFileFromSFTPServer() {
		SFTPUtility.deleteFileWithSpecificPrefixAndSpecificFileExtensionFromLocalDirectory(SFTPproperties.SFTP_FILE_PREFIX.toString(), SFTPproperties.SFTP_ZIP_FILE_EXTENSION.toString(), SFTPproperties.SFTP_LOCAL_DIRECTORY.toString());
		ChannelSftp channel=SFTPUtility.makeSFTPConnection(SFTPproperties.SFTP_HOST.toString(), SFTPproperties.SFTP_PORT.toInteger(), SFTPproperties.SFTP_USERNAME.toString(), SFTPproperties.SFTP_PASSWORD.toString());
		Vector<LsEntry> fileList=SFTPUtility.listAllFilesInSpecifiedRemoteDirectory(SFTPproperties.SFTP_REMOTE_DIRECTORY.toString(), channel);
		//generate todays date
		SimpleDateFormat dateFormat = new SimpleDateFormat(SFTPproperties.SFTP_FILE_DATEFORMAT.toString());
		String todayDate=dateFormat.format(new Date());
		SFTPUtility.downloadFileWithSpecificPrefixAndSpecificExtensionForSpecificDateInLocalDirectory(SFTPproperties.SFTP_FILE_PREFIX.toString(), SFTPproperties.SFTP_ZIP_FILE_EXTENSION.toString(), todayDate,SFTPproperties.SFTP_LOCAL_DIRECTORY.toString(), fileList, channel);
		
	}
	
	
	
	
	
}

