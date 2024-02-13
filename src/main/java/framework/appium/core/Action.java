package framework.appium.core;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ImmutableMap;

import framework.core.Element;
import framework.utils.TestProperties;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class Action{

	private AppiumDriver driver;
	public framework.core.Action getWebAction;
	
	public Action(AppiumDriver driver, HashMap<String, Object> storageMap) {
		this.driver = driver;
		getWebAction=new framework.core.Action(driver,storageMap);
	}

	public AppiumDriver getDriver() {
		return driver;
	}

	
	/**
	 * @desc gets by and returns it
	 * @param elem
	 * @return By
	 */
	public By by(Element elem) {
		By by=null;
		if (elem.elementType.equalsIgnoreCase("accessibilityid")) {
			by=AppiumBy.accessibilityId(elem.getElementValue());
		}
		else if (elem.elementType.equalsIgnoreCase("name")) {
			by=AppiumBy.name(elem.getElementValue());
		}
		else if (elem.elementType.equalsIgnoreCase("xpath")) {
			by=AppiumBy.xpath(elem.getElementValue());
		}
		else if (elem.elementType.equalsIgnoreCase("runtimeid")) {
			by=AppiumBy.id(elem.getElementValue());
		}
		else if (elem.elementType.equalsIgnoreCase("class")) {
			by=AppiumBy.className(elem.getElementValue());
		}
		else if (elem.elementType.equalsIgnoreCase("tagname")) {
			by=AppiumBy.tagName(elem.getElementValue());
		}
		else {
			throw new RuntimeException("Element find by type is not defined");
		}
		return by;
	}
	
	/**
	 * @desc this method finds element and returns it
	 * @param elem
	 * @return WebElement
	 */
	public WebElement findElement(Element elem) {
		return driver.findElement(by(elem));
	}
	
	/**
	 * @desc this method find elements and returns it
	 * @param elem
	 * @return List<WebElement>
	 */
	public List<WebElement> findElements(Element elem) {
		return driver.findElements(by(elem));
	}
	 
	
	
	/**
	 * @Description This method will wait for the visibility of element located by xpath for the given timeout specified in properties file
	 * @param element
	 * @throws runtime exception
	 */
	public Action waitForElementToBeVisible(Element elem) {
			WebDriverWait wait = new WebDriverWait(AppiumDriverFactory.getDriver(),Duration.ofSeconds((int) TestProperties.TEST_TIMEOUT.getProperty()));
			try {
				wait.until(ExpectedConditions.visibilityOf(findElement(elem)));
		} catch (Exception e) {
			throw new RuntimeException(elem.getElementName() + " not visible in the page. ", e);
		}
		return this;
	}
	
	
	/**
	 * @Description This method will wait for the visibility of element located for the time given in arguments
	 * @param element
	 * @param timeOut
	 * @throws runtime exception
	 */   
	public Action waitForElementToBeVisible(Element elem, int timeOut) {
		WebDriverWait wait = new WebDriverWait(AppiumDriverFactory.getDriver(),Duration.ofSeconds(timeOut));
	try {
		wait.until(ExpectedConditions.visibilityOf(findElement(elem)));
	} catch (Exception e) {
		throw new RuntimeException(elem.getElementName() + " not visible in the page. ", e);
	}
	return this;
	}
	
	/**
	 * @desc validates weather element is visible or not
	 * @param elem
	 * @return
	 */
	public boolean isElementVisible(Element elem) {
		try {
			waitForElementToBeVisible(elem);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * @desc validates weather element is visible or not
	 * @param elem, timeOut
	 * @return
	 */
	public boolean isElementVisible(Element elem, int timeOut) {
		try {
			waitForElementToBeVisible(elem, timeOut);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * @Description This method will wait for the presence of element located by given locator for the given timeout specified in properties file
	 * @param element
	 * @throws runtime exception
	 */
	public Action waitForElementPresence(Element elem) {
		WebDriverWait wait = new WebDriverWait(AppiumDriverFactory.getDriver(),Duration.ofSeconds((int) TestProperties.TEST_TIMEOUT.toInteger()));
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(by(elem)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(elem.getElementName() + " not present in the page. ", e);
		}
		return this;
	}
	
	
	/**
	 * @Description This method will wait for the presence of element located by given locator for the given timeout specified in method argument
	 * @param element
	 * @throws runtime exception
	 */
	public Action waitForElementPresence(Element elem, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(AppiumDriverFactory.getDriver(),Duration.ofSeconds(timeoutInSeconds));
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(by(elem)));
		} catch (Exception e) {
			throw new RuntimeException(elem.getElementValue() + " not present in the page. ", e);
		}
		return this;
	}
	
	
	/**
	 * @Description : this method will wait for the presence element located for the given timeout specified in properties file. Returns true if found else false if not found.
	 * @param element
	 * @throws runTimeException
	 */
	public boolean isElementPresent(Element element) {
		try {
			waitForElementPresence(element);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @Description : this method will wait for the presence element located for the given timeout specified in the method arguments. Returns true if found else false if not found.
	 * @param element,timeOutInSeconds
	 * @throws runTimeException
	 */
	public boolean isElementPresent(Element element, int timeOutInSeconds) {
		try {
			waitForElementPresence(element,timeOutInSeconds);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
	/**
	 * @Description : this method will wait for the disappearance of element located by xpath for the given timeout specified in the properties file.
	 * @param element, timeOutInSeconds
	 * @throws runTimeException
	 */
	public Action waitForElementInvisibility(Element elem) {
		WebDriverWait wait = new WebDriverWait(AppiumDriverFactory.getDriver(),Duration.ofSeconds((int)TestProperties.TEST_TIMEOUT.getProperty()));
		try {
			wait.until(ExpectedConditions.invisibilityOf(findElement(elem)));
		} catch (Exception e) {
			throw new RuntimeException(elem.getElementValue() + " still visible in the  page ", e);
		}
		return this;
	}
	
	/**
	 * @Description : this method will wait for the disappearance of element located by xpath for the given timeout specified in the method argument.
	 * @param element, timeOutInSeconds
	 * @throws runTimeException
	 */
	public Action waitForElementInvisibility(Element elem, int timeOut) {
		WebDriverWait wait = new WebDriverWait(AppiumDriverFactory.getDriver(),Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.invisibilityOf(findElement(elem)));
		} catch (Exception e) {
			throw new RuntimeException(elem.getElementValue() + " still visible in the  page ", e);
		}
		return this;
	}
	
	
	
	/**
	 * @Description This method will wait for the element to be clickable for the given timeout specified in properties file
	 * @param element
	 * @throws runtime exception
	 */
	public Action waitForElementToBeClickable(Element elem) {
		try {
			WebDriverWait wait = new WebDriverWait(AppiumDriverFactory.getDriver(),Duration.ofSeconds((int) TestProperties.TEST_TIMEOUT.getProperty()));
			wait.until(ExpectedConditions.elementToBeClickable(findElement(elem)));
		} catch (Exception e) {
			throw new RuntimeException(elem.getElementValue() + " not clickable in the page. ", e);
		}
		return this;
	}
	
	
	
	public Action click(Element elem) {
		waitForElementPresence(elem);
		waitForElementToBeVisible(elem);
		waitForElementToBeClickable(elem);
		try {
			findElement(elem).click();
		} catch (Exception e) {
			throw new RuntimeException("Not able to click on element "+elem.getElementName(),e);
		}
		return this;
	}
	
	
	
	/**
	 * @Description : this this method will wait for presence,visible and clickable of element and will first clear and then it will send keys 
	 * @param element
	 * @param value
	 * @throws runTimeException
	 */
	public Action sendKeys(Element elem,String value) {
		waitForElementPresence(elem);
		waitForElementToBeVisible(elem);
		waitForElementToBeClickable(elem);
		try {
			findElement(elem).click();
			findElement(elem).clear();
			findElement(elem).sendKeys(value);
		} catch (Exception e) {
			throw new RuntimeException("Not able to type value "+value + " in the textbox "+elem.getElementName(),e);
		}
		return this;
	}
	
	
	/**
	 * @Description : this this method will wait for presence,visible of element and then it will send keys 
	 * @param element
	 * @param value
	 * @throws runTimeException
	 */
	public Action SendKeys(Element elem,Keys value) {
		waitForElementPresence(elem);
		waitForElementToBeVisible(elem);
		try {
			findElement(elem).sendKeys(value);
		} catch (Exception e) {
			throw new RuntimeException("Not able to type value "+value + " in the textbox "+elem.getElementName(),e);
		}
		return this;
	}
		

	
	
	/**
	 * @Description : this method will wait for the presence of element of element and return the value of attribute specified..
	 * @param element
	 * @throws runTimeException
	 * @returns String{value}
	 */
	public String getAttribute(Element elem, String attribute) {
		String attrValue="";
		waitForElementPresence(elem);
		try {
			attrValue=findElement(elem).getAttribute(attribute);
		} catch (Exception e) {
			throw new RuntimeException("Not able to get the attribute "+attribute+" from element "+elem.getElementName(),e);
		}
		return attrValue;
	}
	

	
	/**
	 * @Description : this method will wait for the presence of element and return the text value
	 * @param element
	 * @throws runTimeException
	 * @returns String{value}
	 */
	public String getText(Element elem) {
		String textValue="";
		waitForElementPresence(elem);
		try {
			textValue=findElement(elem).getText();
		} catch (Exception e) {
			throw new RuntimeException("Not able to get the text from "+elem.getElementName(),e);
		}
		return textValue;
	}
	
	
	
	/**
	 * @desc get element count
	 * @param elem
	 * @return
	 */
	public int getElementCount(Element elem) {
		int elementCount=0;
		waitForElementPresence(elem);
		try {
			elementCount=findElements(elem).size();
		} catch (Exception e) {
			throw new RuntimeException("Not able to find element "+elem.getElementName(),e);
		}
		return elementCount;
	}
	
	
	/**
	 * @Descriotion : This method will wait for presence of element and then return the default selected value / first value of a dropdown.
	 * @param element
	 * @return String{value}
	 */
	public String getDefaultDropDownSelectedValue(Element elem) {
		String value ="";
		waitForElementPresence(elem);
		Select select = null;
			try {
				select = new Select(findElement(elem));
				value=select.getFirstSelectedOption().getText();
			} catch (Exception e) {
			throw new RuntimeException("Not able to get default /first selected value from drop down "+elem.getElementName(),e);
		}
		return value;	
	}
	
	/**
	 * @Descriotion : This method will wait for presence of element and then select the option by given visible text
	 * @param element
	 */
	public Action Select(Element elem,String visibleText) {
		waitForElementPresence(elem);
		Select select = null;
			try {
				select = new Select(findElement(elem));
				select.selectByVisibleText(visibleText);
			} catch (Exception e) {
			throw new RuntimeException("Not able to select the option "+visibleText+" in "+elem.getElementName(),e);
		}
		return this;
	}
	
	
	/**
	 * @Descriotion : This method will wait for presence of element and then select the given value of the dropdown by index.
	 * @param element
	 * @param index
	 */
	public Action select(Element elem,int index) {
		waitForElementPresence(elem);
		Select select = null;
			try {
				select = new Select(findElement(elem));
				select.selectByIndex(index);
		} catch (Exception e) {
			throw new RuntimeException("Not able to select the option which is on index "+index+" from drop down "+elem.getElementName(),e);
		}
		return this;
	}

	
	/**
	 * @desc This method return all available context names including WebView and NativeApp etc for android
	 * @return contextNames
	 */
	public Set<String> getAllContextForAndroid() {
		try {
			return ((AndroidDriver)driver).getContextHandles();
		} catch (Exception e) {
			throw new RuntimeException("Not able to get all contexts");
		}
	}

	/**
	 * @desc this method switches to the given context webview or native for iOS
	 * @param string
	 */
	public Action switchContextForAndroid(String context) {
		try {
			((AndroidDriver)driver).context(context);
		} catch (Exception e) {
			throw new RuntimeException("Not able to switch to the given context "+context);
		}
		return this;
	}
	
	/**
	 * @desc This method return all available context names including WebView and NativeApp etc for iOS
	 * @return contextNames
	 */
	public Set<String> getAllContextForIOS() {
		try {
			return ((IOSDriver)driver).getContextHandles();
		} catch (Exception e) {
			throw new RuntimeException("Not able to get all contexts");
		}
	}

	/**
	 * @desc this method switches to the given context webview or native for android
	 * @param string
	 */
	public Action switchContextForIOS(String context) {
		try {
			((IOSDriver)driver).context(context);
		} catch (Exception e) {
			throw new RuntimeException("Not able to switch to the given context "+context);
		}
		return this;
	}

	
	/**
	 * @desc This method will perform key-press using robot for eg.,  control key, functions key, numeric keys and alphabetic keys
	 * 	     it will also perform sequence of characters if it contains combination of alphanumeric
	 * @param value to be entered
	 * @return Action
	 */
	public Action performKeyBoardKeys(String value) {
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
	 * @Description : this method will scroll into view of a particular element
	 * @param element
	 * @param viewOnTop (if true, the top of element will be aligned to the top of visible area)
	 */
	public Action scrollIntoView(Element elem) {
		
		try {
			boolean canScrollMore = true;
	        while(canScrollMore){
	            canScrollMore = (Boolean) driver.executeScript("mobile: scrollGesture", ImmutableMap.of(
	                   // "left", 100, "top", 100, "width", 600, "height", 600,
	                    "elementId", ((RemoteWebElement) findElement(elem)).getId(),
	                    "direction", "down",
	                    "percent", 1.0
	            ));
	        }
	} catch (Exception e) {
		e.printStackTrace();
		throw new RuntimeException("Not able to scroll to the element "+elem.getElementName(),e);
	}
		return this;
	}

	
	
	


	
}
