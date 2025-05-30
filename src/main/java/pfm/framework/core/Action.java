package pfm.framework.core;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.core.WebDriverFactory;
import framework.testNG.TestNGUtils;
import framework.utils.TestProperties;

public class Action {
	
	private WebDriver driver;
	private HashMap<String, Object> storageMap;

	public Action(WebDriver driver, HashMap<String, Object> storageMap) {
		this.driver = driver;
		this.storageMap = storageMap;
	}

	public WebDriver getDriver() {
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
	 * @Description This method will wait for the visibility of element located by xpath for the given timeout specified in properties file
	 * @param element
	 * @throws runtime exception
	 */
	public Action waitForElementToBeVisible(WebElement elem) {
		WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(),
				Duration.ofSeconds((int) TestProperties.TEST_TIMEOUT.getProperty()));;
		try {
			wait.until(ExpectedConditions.visibilityOf((elem)));
		} catch (Exception e) {
			throw new RuntimeException(elem + " not visible in the page. ", e);
		}
		return this;
	}

	/**
	 * @Description This method will wait for the presence of element located by xpath for the given timeout specified in properties file
	 * @param element
	 * @throws runtime exception
	 */
	public Action waitForElementPresence(By by) {
		WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(),
				Duration.ofSeconds((int) TestProperties.TEST_TIMEOUT.getProperty()));
		try {	
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			throw new RuntimeException("element specified by " + by + " not present in the page. ", e);
		}
		return this;
	}

	/**
	 * @Description This method will wait for the element to be clickable located by xpath for the given timeout specified in properties file
	 * @param element
	 * @throws runtime exception
	 */
	public Action waitForElementToBeClickable(WebElement elem) {
		WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(),
				Duration.ofSeconds((int) TestProperties.TEST_TIMEOUT.getProperty()));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elem));
		} catch (Exception e) {
			throw new RuntimeException(elem + " not clickable in the page. ", e);
		}
		return this;
	}
	
	
	/**
	 * @Description This method will wait for the visibility of element located by xpath for the given timeout specified in method argument
	 * @param element
	 * @throws runtime exception
	 */
	public Action waitForElementToBeVisible(WebElement elem, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(),
				Duration.ofSeconds(timeoutInSeconds));
		try {
			wait.until(ExpectedConditions.visibilityOf(elem));
		} catch (Exception e) {
			throw new RuntimeException(elem + " not visible in the page. ", e);
		}
		return this;
	}

	/**
	 * @Description This method will wait for the presence of element located by xpath for the given timeout specified in method argument
	 * @param element
	 * @throws runtime exception
	 */
	public Action waitForElementPresence(By by, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(),
				Duration.ofSeconds(timeoutInSeconds));
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			throw new RuntimeException("Element located by "+by+" not present in the page. ", e);
		}
		return this;
	}

	/**
	 * @Description This method will wait for the element to be clickable located by xpath for the given timeout specified in method argument
	 * @param element
	 * @throws runtime exception
	 */
	public Action waitForElementToBeClickable(WebElement elem, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(),
				Duration.ofSeconds(timeoutInSeconds));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elem));
		} catch (Exception e) {
			throw new RuntimeException(elem + " not clickable in the page. ", e);
		}
		return this;
	}


	
	/**
	 *@Description : This method will wait for element presence,visible and clickable and then click on the element
	 * @param element
	 * @throws runtime exception
	 */
	public Action click(WebElement elem) {
		waitForElementToBeVisible(elem);
		waitForElementToBeClickable(elem);
		try {
			elem.click();;
		} catch (Exception e) {
			throw new RuntimeException("Not able to click in the " + elem, e);
		}
		return this;

	}

	/**
	 * @Description this method will execute the given javascript code on the given element
	 * @param script
	 * @param element
	 */
	public Action executeJavascript(String script, WebElement elem) {
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript(script, elem);
		} catch (Exception e) {
			throw new RuntimeException("Not able to execute script " + script + " on element " + elem, e);
		}
		return this;
	}

	
	/**
	 * @Description : this this method will wait for presence,visible and clickable of element and will first clear and then it will send keys 
	 * @param element
	 * @param value
	 * @throws runTimeException
	 */
	public Action sendKeys(WebElement elem, String value) {
		waitForElementToBeVisible(elem);
		waitForElementToBeClickable(elem);
		try {
			elem.click();
			elem.clear();
			elem.sendKeys(value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Not able to type the value " + value + " in the textbox " + elem, e);
		}
		return this;
	}

	
	/**
	 * @Description : this this method will wait for presence,visible of element and then it will perform keyboard operation.
	 * @param element
	 * @param value
	 * @throws runTimeException
	 */
	public Action sendKeys(WebElement elem, Keys value) {
		waitForElementToBeVisible(elem);
		try {
			elem.sendKeys(value);
		} catch (Exception e) {
			throw new RuntimeException(
					"Not able to perform the keyboard action " + value + " on the element " + elem,
					e);
		}
		return this;
	}
	
	
	/**
	 * Description : This method will wait until the page gets loaded within time specified in properties file, means wait till document.readyState will be complete.
	 * @param timeout
	 */
	public Action waitForPageLoad() {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds( TestProperties.TEST_TIMEOUT.toInteger()));
		try {
			wait.until(pageLoadCondition);
		} catch (Exception e) {
			throw new RuntimeException("Not able to wait until page get loaded");
		}
		
		return this;
	}
	
	
	/** 
	 * @Description : This method will switch control of window by window name.
	 * @param  : window name
	 */
	public Action switchToWindow(String windowName) {
		TestNGUtils.reportLog("Switch to the window with title"+windowName);
		ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
		for (int i = 0; i < windows.size(); i++) {
			driver.switchTo().window(windows.get(i));
			if (driver.getTitle().trim().equalsIgnoreCase(windowName)) {
				break;
			}
			else if (i==windows.size()) {
				throw new RuntimeException("Window with title "+windowName + " not found");
			}
		}
		return this;
	}
	
	
	/** 
	 * @Description : This method will switch control of window by index.
	 * @param  : window index
	 */
	public Action switchToWindow(int windowNumber) {
		TestNGUtils.reportLog("Switch to the window number"+windowNumber);
		ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(windows.get(windowNumber));
		return this;
	}
	
	
	/** 
	 * @Description : This method will switch to the default(parent) window.
	 */
	public Action switchToWindow() {
		TestNGUtils.reportLog("Switch to the parent/default window");
		driver.switchTo().defaultContent();
		return this;
	}
	
	
	/** 
	 * @Description : This method will switch to the default(parent) frame.
	 */
	public Action switchToFrame() {
		TestNGUtils.reportLog("Switch to the parent/default frame");
		driver.switchTo().parentFrame();
		return this;
	}
	
	
	/** 
	 * @Description : This method will switch to the frame by given element.
	 * @param : frameName
	 */
	public Action switchToFrame(WebElement elem) {
		try {
			driver.switchTo().frame(elem);
		}catch (Exception e) {
			throw new RuntimeException("Not able to swtich to the given frame "+elem,e);
		}
		
		return this;
	}
	
	/**
	 * {@Description : This method waill wait for presence and hover mouse over the given element
	 * @param element
	 * @return
	 */
	public Action hoverOnElement(WebElement elem) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(elem).build().perform();
		} catch (Exception e) {
			throw new RuntimeException("Not able to hover over element "+elem,e);
		}
		return this;
	}
	
	
	
	/**
	 * @Descriotion : This method will wait for presence of element and then return the default selected value / first value of a dropdown.
	 * @param element
	 * @return String{value}
	 */
	public String getDefaultDropDownSelectedValue(WebElement elem) {
		String value ="";
		Select select = null;
		try {
			select = new Select(elem);
			value=select.getFirstSelectedOption().getText();
		} catch (Exception e) {
			throw new RuntimeException("Error occured while fetching default selected value/first value from drop down "+elem,e);
		}
		return value;
	}
	
	/**
	 * @Descriotion : This method will wait for presence of element and then find index of given value(text of select dropdown value) and then select the given value of the dropdown by index.
	 * @param element
	 */
	public Action select(WebElement elem,String selectOption) {
		Select select = null;
		try {
			select = new Select(elem);
			List<WebElement> listOfOptions=select.getOptions();
			int optionIndex=0;
			for (int i = 0; i < listOfOptions.size(); i++) {
				if (listOfOptions.get(i).getText().equalsIgnoreCase(selectOption)) {
					optionIndex=i;
					break;
				}
			}
			select.selectByIndex(optionIndex);
		} catch (Exception e) {
			throw new RuntimeException("Not able to select the option "+selectOption+" in "+elem,e);
		}
		return this;
	}
	
	/**
	 * @Description : this method will wait for the presence element located by xpath for the given timeout specified in properties file. Returns true if found else false if not found.
	 * @param element
	 * @throws runTimeException
	 */
	public boolean isElementPresent(By by) {
		try {
			waitForElementPresence(by);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * @Description : this method will wait for the presence element located by xpath for the given timeout specified in the method arguments. Returns true if found else false if not found.
	 * @param element,timeOutInSeconds
	 * @throws runTimeException
	 */
	public boolean isElementPresent(By by, int timeOutInSeconds) {
		try {
			waitForElementPresence(by,timeOutInSeconds);
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
	public Action waitForElementInvisibility(WebElement elem) {
		WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), Duration.ofSeconds((int)TestProperties.TEST_TIMEOUT.toInteger()));
		try {
			wait.until(ExpectedConditions.invisibilityOf(elem));
		} catch (Exception e) {
			throw new RuntimeException(elem + " still visible in the page.",e);
		}
		return this;
	}
	
	
	/**
	 * @Description : this method will wait for the visibility element located by xpath for the given timeout specified in the method argument. Returns true if found else false if not found.
	 * @param element,timeOutInSeconds
	 * @throws runTimeException
	 */
	public boolean isElementVisible(WebElement element, int timeOutInSeconds) {
		try {
			waitForElementToBeVisible(element,timeOutInSeconds);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @Description : this method will wait for the visibility element located by xpath for the given timeout specified in the properties fioe. Returns true if found else false if not found.
	 * @param element,timeOutInSeconds
	 * @throws runTimeException
	 */
	public boolean isElementVisible(WebElement element) {
		try {
			waitForElementToBeVisible(element);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
		
	
	
	/**
	 * @Description : this method will wait for the presence of element and return the value of attribute specified..
	 * @param element
	 * @throws runTimeException
	 * @returns String{value}
	 */
	public String getAttribute(By by,String attribute) {
		String attributeValue="";
		waitForElementPresence(by);
		WebElement element=null;
		try {
			element=driver.findElement(by);
			attributeValue=element.getAttribute(attribute);
		} catch (Exception e) {
			throw new RuntimeException("Not able to get the attribute value from "+element,e);
		}
		return attributeValue;
	}
	
	/**
	 * @Description : this method will wait for the presence of element and return the text value
	 * @param element
	 * @throws runTimeException
	 * @returns String{value}
	 */
	public String getText(By by) {
		String textValue="";
		waitForElementPresence(by);
		WebElement element=null;
		try {
			element = driver.findElement(by);
			textValue=element.getText();
		} catch (Exception e) {
			throw new RuntimeException("Not able to get the text from "+element,e);
		}
		return textValue;
	}
	
	
	/**
	 * Description : This method will wait for the presence of element and return the count of number of element define by the elment
	 * @param element
	 * @throws runTimeException
	 */
	public Integer getElementCount(By by) {
		Integer countOfElements=0;
		waitForElementPresence(by);
		try {
			countOfElements=driver.findElements(by).size();
		} catch (Exception e) {
				throw new RuntimeException("Not able to find element "+by,e);
		}
		return countOfElements;
	}
	
	
	/**
	 * @Description : this method will scroll into view of a particular element
	 * @param element
	 * @param viewOnTop (if true, the top of element will be aligned to the top of visible area)
	 */
	public Action scrollIntoView(By by, boolean viewOnTop) {
		waitForElementPresence(by);
		WebElement element=null;
		try {
			element = driver.findElement(by);
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView("+viewOnTop+");", element);
		} catch (Exception e) {
			throw new RuntimeException("Not able to find the element "+element,e);
		}
		return this;
	}
	
	
	/**
	 * @Descriotion : This method will wait for presence of element and then select the given value of the dropdown by index.
	 * @param element
	 * @param index
	 */
	public Action Select(WebElement elem,int index) {
		Select select = null;
		waitForElementToBeVisible(elem);
		try {
			select=new Select(elem);
			select.selectByIndex(index);
		} catch (Exception e) {
			throw new RuntimeException("Not able to select the option which is on index "+index+" in "+elem,e);
		}
		return this;
	}
	
	
	/**
	 * @desc this method will wait for specific time
	 * @param seconds
	 */
	public Action waitFor(int seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
	
	



}
