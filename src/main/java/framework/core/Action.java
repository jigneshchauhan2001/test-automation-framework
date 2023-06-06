package framework.core;

import static org.testng.Assert.assertThrows;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.TestNG;

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

	public HashMap<String, Object> getHashKey() {
		return storageMap;
	}

	public synchronized void storeKeyValue(String key, String value) {
		storageMap.put(key, value);
	}

	public synchronized Object retrieveKeyValue(String key) {
		Object hashValue = storageMap.get(key);
		return hashValue;
	}

	public Action deleteKeyValue(String key) {
		storageMap.remove(key);
		return this;
	}

	
	
	/**
	 * @Description This method will wait for the visibility of element located by xpath for the given timeout specified in properties file
	 * @param element
	 * @throws runtime exception
	 */
	public Action WaitForElementToBeVisible(Element element) {
		try {
			WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(),
					Duration.ofSeconds((int) TestProperties.TEST_TIMEOUT.getProperty()));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.getElementValue())));
		} catch (Exception e) {
			throw new RuntimeException(element.getElementValue() + " not visible in the page. ", e);
		}
		return this;
	}

	/**
	 * @Description This method will wait for the presence of element located by xpath for the given timeout specified in properties file
	 * @param element
	 * @throws runtime exception
	 */
	public Action WaitForElementPresence(Element element) {
		try {
			WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(),
					Duration.ofSeconds((int) TestProperties.TEST_TIMEOUT.getProperty()));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element.getElementValue())));
		} catch (Exception e) {
			throw new RuntimeException(element.getElementValue() + " not present in the page. ", e);
		}
		return this;
	}

	/**
	 * @Description This method will wait for the element to be clickable located by xpath for the given timeout specified in properties file
	 * @param element
	 * @throws runtime exception
	 */
	public Action WaitForElementToBeClickable(Element element) {
		try {
			WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(),
					Duration.ofSeconds((int) TestProperties.TEST_TIMEOUT.getProperty()));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.getElementValue())));
		} catch (Exception e) {
			throw new RuntimeException(element.getElementValue() + " not clickable in the page. ", e);
		}
		return this;
	}

	
	/**
	 *@Description : This method will wait for element presence,visible and clickable and then click on the element
	 * @param element
	 * @throws runtime exception
	 */
	public Action Click(Element element) {
		try {
			WaitForElementPresence(element);
			WaitForElementToBeVisible(element);
			WaitForElementToBeClickable(element);

			if (TestProperties.MOBILE_EXECUTUION.toBoolean()) {
				ExecuteJavascript("arguments[0].click();", element);
			} else {
				driver.findElement(By.xpath(element.getElementValue())).click();
			}

		} catch (Exception e) {
			throw new RuntimeException("Not able to click in the " + element.getElementName(), e);
		}
		return this;

	}

	/**
	 * @Description this method will execute the given javascript code on the given element
	 * @param script
	 * @param element
	 */
	public Action ExecuteJavascript(String script, Element element) {
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript(script, driver.findElement(By.xpath(element.getElementValue())));
		} catch (Exception e) {
			throw new RuntimeException(
					"Not able to execute script " + script + " on element " + element.getElementName(), e);
		}
		return this;
	}

	
	/**
	 * @Description : this this method will wait for presence,visible and clickable of element and will first clear and then it will send keys 
	 * @param element
	 * @param value
	 * @throws runTimeException
	 */
	public Action SendKeys(Element element, String value) {
		try {
			WaitForElementPresence(element);
			WaitForElementToBeVisible(element);
			WaitForElementToBeClickable(element);

			driver.findElement(By.xpath(element.getElementValue())).click();
			driver.findElement(By.xpath(element.getElementValue())).clear();
			driver.findElement(By.xpath(element.getElementValue())).sendKeys(value);
		} catch (Exception e) {
			throw new RuntimeException(
					"Not able to type the value " + value + " in the textbox " + element.getElementName(), e);
		}
		return this;
	}

	
	/**
	 * @Description : this this method will wait for presence,visible of element and then it will perform keyboard operation.
	 * @param element
	 * @param value
	 * @throws runTimeException
	 */
	public Action SendKeys(Element element, Keys value) {
		try {
			WaitForElementPresence(element);
			WaitForElementToBeVisible(element);
			driver.findElement(By.xpath(element.getElementValue())).sendKeys(value);
		} catch (Exception e) {
			throw new RuntimeException(
					"Not able to perform the keyboard action " + value + " on the element " + element.getElementName(),
					e);
		}
		return this;
	}

	/**
	 * @Description this method will wait for presence,visible and clickable of element and will send keys only when maskFlag is true & will mask value string as per requirements.
	 * @param element
	 * @param value
	 * @param maskFlag
	 * @return
	 */
	public Action SendKeys(Element element, String value, boolean maskFlag) {
		try {
			// masking
			if (maskFlag == false) {
				return this;
			}
			String maskedData = "";
			if (element.getElementName().equalsIgnoreCase("Worldpay credit card number text field")) {
				if (value.length() == 16) {
					maskedData = value.substring(0, value.length() - 4);
					int cardNumberLength = maskedData.length();
					for (int i = value.length() - 1; i >= cardNumberLength; i--) {
						maskedData = maskedData.concat("*");
					}
				} else {
					maskedData = value.replace(".", "*");
				}
			} else {
				maskedData = value.replace(".", "*");
			}

			WaitForElementPresence(element);
			WaitForElementToBeVisible(element);
			WaitForElementToBeClickable(element);

			driver.findElement(By.xpath(element.getElementValue())).click();
			driver.findElement(By.xpath(element.getElementValue())).clear();
			driver.findElement(By.xpath(element.getElementValue())).sendKeys(value);
		} catch (Exception e) {
			throw new RuntimeException(
					"Not able to type the value " + value + " in the textbox " + element.getElementName(), e);
		}
		return this;
	}

	/**
	 * @Description : Used to wait for specific time
	 * @param seconds
	 */
	public Action WaitFor(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
	
	/**
	 * Description : This method will wait until the page gets loaded within time specified in properties file, means wait till document.readyState will be complete.
	 * @param timeout
	 */
	public Action WaitForPageLoad() {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds( TestProperties.TEST_TIMEOUT.toInteger()));
		wait.until(pageLoadCondition);
		return this;
	}
	
	
	/** 
	 * @Description : This method will switch control of window by window name.
	 * @param  : window name
	 */
	public Action SwitchToWindow(String windowName) {
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
	public Action SwitchToWindow(int windowNumber) {
		TestNGUtils.reportLog("Switch to the window number"+windowNumber);
		ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(windows.get(windowNumber));
		return this;
	}
	
	
	/** 
	 * @Description : This method will switch to the default(parent) window.
	 */
	public Action SwitchToWindow() {
		TestNGUtils.reportLog("Switch to the parent/default window");
		driver.switchTo().defaultContent();
		return this;
	}
	
	
	/** 
	 * @Description : This method will switch to the default(parent) frame.
	 */
	public Action SwitchToFrame() {
		TestNGUtils.reportLog("Switch to the parent/default frame");
		driver.switchTo().parentFrame();
		return this;
	}
	
	/** 
	 * @Description : This method will switch to the given frame xpath.
	 * @param : frameName
	 */
	public Action SwitchToFrame(Element frameName) {
		TestNGUtils.reportLog("Switch to the parent/default frame");
		driver.switchTo().frame(driver.findElement(By.xpath(frameName.getElementValue())));
		return this;
	}
	
	/**
	 * {@Description : This method waill wait for presence,visible and hover mouse over the given element
	 * @param element
	 * @return
	 */
	public Action HoverOnElement(Element element) {
		try {
			WaitForElementPresence(element);
			WaitForElementToBeVisible(element);
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(By.xpath(element.getElementValue()))).build().perform();
		} catch (Exception e) {
			throw new RuntimeException("Error occured while hovering on "+element.getElementName(),e);
		}
		return this;
	}
	
	
	/**
	 * @Descriotion : This method will wait for presence of element and then return the default selected value / first value of a dropdown.
	 * @param element
	 * @return String{value}
	 */
	public String getDefaultDropDownSelectedValue(Element element) {
		TestNGUtils.reportLog("Wait for the element "+element.getElementName() + "and fetch default selected value/first value");
		String value ="";
		try {
			WaitForElementPresence(element);
			Select select = new Select(driver.findElement(By.xpath(element.getElementValue())));
			value=select.getFirstSelectedOption().getText();
		} catch (Exception e) {
			throw new RuntimeException("Error occured while fetching default selected value/first value from drop down "+element.getElementName(),e);
		}
		return value;
	}
	
	/**
	 * @Descriotion : This method will wait for presence of element and then find index of given value(text of select dropdown value) and then select the given value of the dropdown by index.
	 * @param element
	 */
	public String Select(Element element,String selectOption) {
		TestNGUtils.reportLog("Wait for the element "+element.getElementName() + "and then select the option "+selectOption);
		String value ="";
		try {
			WaitForElementPresence(element);
			Select select = new Select(driver.findElement(By.xpath(element.getElementValue())));
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
			throw new RuntimeException("Not avle to select the option "+selectOption+" in "+element.getElementName(),e);
		}
		return value;
	}
	
	/**
	 * @Description : this method will wait for the presence element located by xpath for the given timeout specified in properties file. Returns true if found else false if not found.
	 * @param element
	 * @throws runTimeException
	 */
	public boolean IsElementPresent(Element element) {
		try {
			WaitForElementPresence(element);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * @Description : this method will wait for the disappearance of element located by xpath for the given timeout specified in properties file
	 * @param element
	 * @throws runTimeException
	 */
	public Action waitForElementInvisibility(Element element) {
		try {
			WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), Duration.ofSeconds((int)TestProperties.TEST_TIMEOUT.toInteger()));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(element.getElementValue())));
		} catch (Exception e) {
			throw new RuntimeException(element.getElementName() + " still visible in the page.",e);
		}
		return this;
	}
	
	
	/**
	 * @Description : this method will wait for the visibility element located by xpath for the given timeout specified in properties file. Returns true if found else false if not found.
	 * @param element
	 * @throws runTimeException
	 */
	public boolean IsElementVisible(Element element) {
		try {
			WaitForElementToBeVisible(element);
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
	public String GetAttribute(Element element,String attribute) {
		String attributeValue="";
		try {
			WaitForElementPresence(element);
			attributeValue=driver.findElement(By.xpath(element.getElementValue())).getAttribute(attribute);
		} catch (Exception e) {
			throw new RuntimeException("Not able to get the attribute value from "+element.getElementName(),e);
		}
		return attributeValue;
	}
	
	
	/**
	 * Description : This method will wait for the presence of element and return the count of number of element define by the elment
	 * @param element
	 * @throws runTimeException
	 */
	public Integer GetElementCount(Element element) {
		Integer countOfElements=0;
		try {
			WaitForElementPresence(element);
			List<WebElement> elements = driver.findElements(By.xpath(element.getElementValue()));
			countOfElements=elements.size();
		} catch (Exception e) {
				throw new RuntimeException("Not able to find element "+element.getElementValue(),e);
		}
		return countOfElements;
	}
	
	
	/**
	 * @Description : this method will scroll into view of a particular element
	 * @param element
	 * @param viewOnTop (if true, the top of element will be aligned to the top of visible area)
	 */
	public Action ScrollIntoView(Element element, boolean viewOnTop) {
		try {
			WaitForElementPresence(element);
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView("+viewOnTop+");", driver.findElement(By.xpath(element.getElementValue())));
		} catch (Exception e) {
			throw new RuntimeException("Not able to find the element "+element.getElementName(),e);
		}
		return this;
	}
	
	
	/**
	 * @Descriotion : This method will wait for presence of element and then select the given value of the dropdown by index.
	 * @param element
	 * @param index
	 */
	public String Select(Element element,int index) {
		TestNGUtils.reportLog("Wait for the element "+element.getElementName() + "and then select the option which is on index "+index);
		String value ="";
		try {
			WaitForElementPresence(element);
			Select select = new Select(driver.findElement(By.xpath(element.getElementValue())));
			select.selectByIndex(index);
			
		} catch (Exception e) {
			throw new RuntimeException("Not able to select the option which is on index "+index+" in "+element.getElementName(),e);
		}
		return value;
	}
	
	
	
	
	
	
	

}
