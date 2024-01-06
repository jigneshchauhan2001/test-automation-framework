package framework.appium.core;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import framework.core.Element;
import framework.utils.TestProperties;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class Action {

	private AppiumDriver driver;
	private HashMap<String, Object> storageMap;
	
	
	public Action(AppiumDriver driver, HashMap<String, Object> storageMap) {
		this.driver = driver;
		this.storageMap = storageMap;
	}

	public AppiumDriver getDriver() {
		return driver;
	}

	/**
	 * @desc this method will store given value against the given key in the hashmap
	 * @param key
	 * @param value
	 * @return
	 */
	public synchronized void storeKeyValue(String key,Object value) {
		storageMap.put(key,value);
	}
	
	/**
	 * @desc this method will return the value of given key from storage hashmap
	 * @param key
	 * @return Object
	 */
	public synchronized Object retrieveKeyValue(String key) {
		return storageMap.get(key);
	}
	
	/** 
	 * @desc this method will delete the value of given key from storage hashmap
	 * @param key
	 */
	public synchronized void deleteKeyValue(String key) {
		storageMap.remove(key);
	}
	
	
	public HashMap<String, Object> getHashKey() {
		return storageMap;
	}
	
	
	
	/**
	 * @Description This method will wait for the visibility of element located for the time specified in properties file
	 * @param element
	 * @throws runtime exception
	 */
	public Action WaitForElementToBeVisible(Element element) {
		int timeOut = Integer.valueOf(TestProperties.TEST_TIMEOUT.toString());
		boolean value=false;
		for (int i = 0; i < timeOut/10; i++) {
			if (IsElementVisible(element)) {
				value=true;
				break;
			}else {
				waitFor(5); // sleep for 2 seconds 
			}
		}
		if (value==false) {
			throw new RuntimeException("Element "+element.getElementName() + " not visible in the page");
		}
		return this;
	}
	
	

	/**
	 * @Description This method will wait for the visibility of element located for the time given in arguments
	 * @param element
	 * @param timeOut
	 * @throws runtime exception
	 */   
	public Action WaitForElementToBeVisible(Element element, int timeOut) {
		boolean value=false;
		for (int i = 0; i < timeOut/10; i++) {
			if (IsElementVisible(element)) {
				value=true;
				break;
			}else {
				waitFor(5); // sleep for 2 seconds 
			}
		}
		if (value==false) {
			throw new RuntimeException("Element "+element.getElementName() + " not visible in the page");
		}
		return this;
	}
	
	
	public boolean IsElementVisible(Element elem) {
		boolean value=false;
		try {
			if (elem.elementValue.equalsIgnoreCase("accessibilityid")) {
				value=driver.findElement(AppiumBy.accessibilityId(elem.getElementValue())).isDisplayed();
			}
			else if (elem.elementValue.equalsIgnoreCase("name")) {
				value=driver.findElement(AppiumBy.name(elem.getElementValue())).isDisplayed();
			}
			else if (elem.elementValue.equalsIgnoreCase("runtimeid")) {
				value=driver.findElement(AppiumBy.id(elem.getElementValue())).isDisplayed();
			}
			else if (elem.elementValue.equalsIgnoreCase("tagname")) {
				value=driver.findElement(AppiumBy.tagName(elem.getElementValue())).isDisplayed();
			}
			else if (elem.elementValue.equalsIgnoreCase("class")) {
				value=driver.findElement(AppiumBy.className(elem.getElementValue())).isDisplayed();
			}
			else if (elem.elementValue.equalsIgnoreCase("xpath")) {
				value=driver.findElement(AppiumBy.xpath(elem.getElementValue())).isDisplayed();
			}else {
				throw new RuntimeException("Element find by type is not defined");
			}
			
		} catch (Exception e) {
			// catch exception do nothing
		}
		return value;
	}
	
	
	
	public Action Click(Element elem) {
		WaitForElementToBeVisible(elem);
		try {
			if (elem.elementValue.equalsIgnoreCase("accessibilityid")) {
				driver.findElement(AppiumBy.accessibilityId(elem.getElementValue())).click();
			}
			else if (elem.elementValue.equalsIgnoreCase("name")) {
				driver.findElement(AppiumBy.name(elem.getElementValue())).click();
			}
			else if (elem.elementValue.equalsIgnoreCase("runtimeid")) {
				driver.findElement(AppiumBy.id(elem.getElementValue())).click();
			}
			else if (elem.elementValue.equalsIgnoreCase("tagname")) {
				driver.findElement(AppiumBy.tagName(elem.getElementValue())).click();
			}
			else if (elem.elementValue.equalsIgnoreCase("class")) {
				driver.findElement(AppiumBy.className(elem.getElementValue())).click();
			}
			else if (elem.elementValue.equalsIgnoreCase("xpath")) {
				driver.findElement(AppiumBy.xpath(elem.getElementValue())).click();
			}else {
				throw new RuntimeException("Element find by type is not defined");
			}
		} catch (Exception e) {
			throw new RuntimeException("Not able to click on element "+elem.getElementName(),e);
		}
		return this;
	}
	
	public Action SendKeys(Element elem,String value) {
		WaitForElementToBeVisible(elem);
		try {
			if (elem.elementValue.equalsIgnoreCase("accessibilityid")) {
				driver.findElement(AppiumBy.accessibilityId(elem.getElementValue())).click();
				driver.findElement(AppiumBy.accessibilityId(elem.getElementValue())).clear();
				driver.findElement(AppiumBy.accessibilityId(elem.getElementValue())).sendKeys(value);
			}
			else if (elem.elementValue.equalsIgnoreCase("name")) {
				driver.findElement(AppiumBy.name(elem.getElementValue())).click();
				driver.findElement(AppiumBy.name(elem.getElementValue())).clear();
				driver.findElement(AppiumBy.name(elem.getElementValue())).sendKeys(value);
			}
			else if (elem.elementValue.equalsIgnoreCase("runtimeid")) {
				driver.findElement(AppiumBy.id(elem.getElementValue())).click();
				driver.findElement(AppiumBy.id(elem.getElementValue())).clear();
				driver.findElement(AppiumBy.id(elem.getElementValue())).sendKeys(value);
			}
			else if (elem.elementValue.equalsIgnoreCase("tagname")) {
				driver.findElement(AppiumBy.tagName(elem.getElementValue())).click();
				driver.findElement(AppiumBy.tagName(elem.getElementValue())).clear();
				driver.findElement(AppiumBy.tagName(elem.getElementValue())).sendKeys(value);
			}
			else if (elem.elementValue.equalsIgnoreCase("class")) {
				driver.findElement(AppiumBy.className(elem.getElementValue())).click();
				driver.findElement(AppiumBy.className(elem.getElementValue())).clear();
				driver.findElement(AppiumBy.className(elem.getElementValue())).sendKeys(value);
			}
			else if (elem.elementValue.equalsIgnoreCase("xpath")) {
				driver.findElement(AppiumBy.xpath(elem.getElementValue())).click();
				driver.findElement(AppiumBy.xpath(elem.getElementValue())).clear();
				driver.findElement(AppiumBy.xpath(elem.getElementValue())).sendKeys(value);
			}else {
				throw new RuntimeException("Element find by type is not defined");
			}
		} catch (Exception e) {
			throw new RuntimeException("Not able to type value "+value + " in the textbox "+elem.getElementName());
		}
		return this;
	}
	
	
	public String getAttribute(Element elem, String attribute) {
		String attrValue="";
		WaitForElementToBeVisible(elem);
		try {
			if (elem.elementValue.equalsIgnoreCase("accessibilityid")) {
				 attrValue=driver.findElement(AppiumBy.accessibilityId(elem.getElementValue())).getAttribute(attribute);
			}
			else if (elem.elementValue.equalsIgnoreCase("name")) {
				attrValue=driver.findElement(AppiumBy.name(elem.getElementValue())).getAttribute(attribute);
			}
			else if (elem.elementValue.equalsIgnoreCase("runtimeid")) {
				attrValue=driver.findElement(AppiumBy.id(elem.getElementValue())).getAttribute(attribute);
			}
			else if (elem.elementValue.equalsIgnoreCase("tagname")) {
				attrValue=driver.findElement(AppiumBy.tagName(elem.getElementValue())).getAttribute(attribute);
			}
			else if (elem.elementValue.equalsIgnoreCase("class")) {
				attrValue=driver.findElement(AppiumBy.className(elem.getElementValue())).getAttribute(attribute);
			}
			else if (elem.elementValue.equalsIgnoreCase("xpath")) {
				attrValue=driver.findElement(AppiumBy.xpath(elem.getElementValue())).getAttribute(attribute);
			}else {
				throw new RuntimeException("Element find by type is not defined");
			}
		} catch (Exception e) {
			throw new RuntimeException("Not able to get the attribute "+attribute+" from element "+elem.getElementName(),e);
		}
		return attrValue;
	}
	
	
	
	
	public int getElementCount(Element elem) {
		int elementCount=0;
		WaitForElementToBeVisible(elem);
		try {
			if (elem.elementValue.equalsIgnoreCase("accessibilityid")) {
				elementCount=driver.findElements(AppiumBy.accessibilityId(elem.getElementValue())).size();
			}
			else if (elem.elementValue.equalsIgnoreCase("name")) {
				elementCount=driver.findElements(AppiumBy.name(elem.getElementValue())).size();
			}
			else if (elem.elementValue.equalsIgnoreCase("runtimeid")) {
				elementCount=driver.findElements(AppiumBy.id(elem.getElementValue())).size();
			}
			else if (elem.elementValue.equalsIgnoreCase("tagname")) {
				elementCount=driver.findElements(AppiumBy.tagName(elem.getElementValue())).size();
			}
			else if (elem.elementValue.equalsIgnoreCase("class")) {
				elementCount=driver.findElements(AppiumBy.className(elem.getElementValue())).size();
			}
			else if (elem.elementValue.equalsIgnoreCase("xpath")) {
				elementCount=driver.findElements(AppiumBy.xpath(elem.getElementValue())).size();
			}else {
				throw new RuntimeException("Element find by type is not defined");
			}
		} catch (Exception e) {
			throw new RuntimeException("Not able to find element "+elem.getElementName(),e);
		}
		return elementCount;
	}
	
	
	public Action WaitForElementInvisibility(Element elem) {
		int timeOut=Integer.valueOf(TestProperties.TEST_TIMEOUT.toString());
		boolean notVisible=false;
		for (int i = 0; i < timeOut/10; i++) {
			if (IsElementVisible(elem)==false) {
				notVisible=true;
				break;
			}
			else {
				waitFor(5);
			}
		}
		if (notVisible==false) throw new RuntimeException(elem.getElementName() + " element still visible in the page");
		return this;
	}
	
	public Action WaitForElementInvisibility(Element elem, int timeOut) {
		boolean notVisible=false;
		for (int i = 0; i < timeOut/10; i++) {
			if (IsElementVisible(elem)==false) {
				notVisible=true;
				break;
			}
			else {
				waitFor(5);
			}
		}
		if (notVisible==false) throw new RuntimeException(elem.getElementName() + " element still visible in the page");
		return this;
	}
	
	
	/**
	 * @desc This method will perform key-press using robot for eg.,  control key, functions key, numeric keys and alphabetic keys
	 * 	     it will also perform sequence of characters if it contains combination of alphanumeric
	 * @param value to be entered
	 * @return Action
	 */
	public Action PerformKeyBoardKeys(String value) {
		try {
			Robot robot = new Robot();
			switch (value) {
			case KeyBoardConstants.CONTROL_KEY: {
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				break;
			}
			case KeyBoardConstants.SHIFT_KEY: {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				break;
			}
			case KeyBoardConstants.ENTER_KEY: {
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				break;
			}
			case KeyBoardConstants.BACKSPACE_KEY: {
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				break;
			}
			case KeyBoardConstants.ALT_KEY: {
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyRelease(KeyEvent.VK_ALT);
				break;
			}
			case KeyBoardConstants.SPACE_KEY: {
				robot.keyPress(KeyEvent.VK_SPACE);
				robot.keyRelease(KeyEvent.VK_SPACE);
				break;
			}
			case KeyBoardConstants.ESC_KEY: {
				robot.keyPress(KeyEvent.VK_ESCAPE);
				robot.keyRelease(KeyEvent.VK_ESCAPE);
				break;
			}
			case KeyBoardConstants.TAB_KEY: {
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				break;
			}
			case KeyBoardConstants.F1_KEY:
			case KeyBoardConstants.F2_KEY:
			case KeyBoardConstants.F3_KEY:
			case KeyBoardConstants.F4_KEY:
			case KeyBoardConstants.F5_KEY:
			case KeyBoardConstants.F6_KEY:
			case KeyBoardConstants.F7_KEY:
			case KeyBoardConstants.F8_KEY:
			case KeyBoardConstants.F9_KEY:
			case KeyBoardConstants.F10_KEY:
			case KeyBoardConstants.F11_KEY:
			case KeyBoardConstants.F12_KEY:
				int fKeyIndex=Integer.parseInt(value.substring(1));
				robot.keyPress(KeyEvent.VK_F1+fKeyIndex-1);
				break;
			default:
				boolean upperCaseeFlag=false;
				for (char c : value.toCharArray()) {
					if (Character.isLetter(c)&&Character.isUpperCase(c)) {
						robot.keyPress(KeyEvent.VK_SHIFT);
						upperCaseeFlag=true;
					}
					int keyCode=KeyEvent.getExtendedKeyCodeForChar(c);
					if (keyCode!=KeyEvent.VK_UNDEFINED) {
						robot.keyPress(keyCode);
						robot.keyRelease(keyCode);
						if (upperCaseeFlag) {
							robot.keyRelease(KeyEvent.VK_SHIFT);
							upperCaseeFlag=false;
						}
					}
					else {
						throw new RuntimeException("Unsupported Character entered: "+c);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Unable to press key "+value + " using robot");
		}
		return this;
	}
	
	
	
	
	
	/**
	 * @desc this method will wait for specific time
	 * @param seconds
	 */
	public void waitFor(int seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
}
