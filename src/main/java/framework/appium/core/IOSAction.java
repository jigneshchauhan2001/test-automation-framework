package framework.appium.core;

import java.util.HashMap;
import java.util.Set;

import org.openqa.selenium.remote.RemoteWebElement;

import com.google.common.collect.ImmutableMap;

import framework.core.Element;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;

public class IOSAction {

	
	
	public AppiumDriver driver;
	private HashMap<String, Object> storageMap;
	public Action action;

	public IOSAction(AppiumDriver driver, HashMap<String, Object> storageMap, Action action) {
		this.driver=(IOSDriver) driver;
		this.storageMap=storageMap;
		this.action=action;
	}
	
	
	/**
	 * @desc This method return all available context names including WebView and NativeApp etc for iOS
	 * @return contextNames
	 */
	public Set<String> getAllContext() {
		try {
			return ((IOSDriver)driver).getContextHandles();
		} catch (Exception e) {
			throw new RuntimeException("Not able to get all contexts");
		}
	}

	
	/**
	 * @desc this method switches to the given context webview or native for iOS
	 * @param string
	 */
	public IOSAction switchContext(String context) {
		try {
			((IOSDriver)driver).context(context);
		} catch (Exception e) {
			throw new RuntimeException("Not able to switch to the given context "+context);
		}
		return this;
	}

	
	
	/**
	 * @Description : this method will scroll into view till element is visible.
	 *  here elementToFound is element that you want to found and scrollElement is element by whose size you want to scroll into page
	 * @param scrollElement,elementToFound, direction(up, down, left, right)
	 * @param 
	 */
	public IOSAction scrollIntoView(Element scrollElement,Element elementToFound,String direction) {
		direction=direction.toLowerCase();
		try {
			boolean canScrollMore = true;
	        while(canScrollMore){
	            canScrollMore = (Boolean) driver.executeScript("mobile: scrollGesture", ImmutableMap.of(
	                    "elementId", ((RemoteWebElement) action.findElement(scrollElement)).getId(),
	                    "direction", direction,
	                    "percent", 1.0
	            ));
	            if (action.isElementVisible(elementToFound,5)) {
	 					canScrollMore=false;
				}
	        }
	} catch (Exception e) {
		throw new RuntimeException("Not able to scroll to the element "+elementToFound.getElementName(),e);
	}
		return this;
	}

	
	
	/**
	 * @Description : this method will scroll till end
	 *  here scrollElement is element by whose size you want to scroll into page
	 * @param scrollElement, direction(up, down, left, right)
	 * @param 
	 */
	public IOSAction scrollToEnd(Element scrollElement,String direction) {
		direction=direction.toLowerCase();
		try {
			boolean canScrollMore = true;
	        while(canScrollMore){
	            canScrollMore = (Boolean) driver.executeScript("mobile: scrollGesture", ImmutableMap.of(
	                    "elementId", ((RemoteWebElement) action.findElement(scrollElement)).getId(),
	                    "direction", direction,
	                    "percent", 1.0
	            ));
	        }
	} catch (Exception e) {
		throw new RuntimeException("Not able to scroll to the end. ",e);
	}
		return this;
	}

	
	
	

}
