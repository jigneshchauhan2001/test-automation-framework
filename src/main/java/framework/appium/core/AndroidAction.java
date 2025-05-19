package framework.appium.core;

import java.time.Duration;
import java.util.HashMap;
import java.util.Set;

import org.openqa.selenium.remote.RemoteWebElement;

import com.fasterxml.jackson.databind.node.BooleanNode;
import com.google.common.collect.ImmutableMap;

import framework.core.Element;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.appmanagement.AndroidInstallApplicationOptions;
import io.appium.java_client.android.appmanagement.AndroidRemoveApplicationOptions;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.appmanagement.BaseRemoveApplicationOptions;

public class AndroidAction {

	// access more methods by giving . -> methods like battery info, device time etc you can find out.
	//((AndroidDriver)driver).
	
	public AppiumDriver driver;
	private HashMap<String, Object> storageMap;
	public Action action;

	public AndroidAction(AppiumDriver driver, HashMap<String, Object> storageMap,Action action) {
		this.driver=driver;
		this.storageMap=storageMap;
		this.action=action;
	}


	/**
	 * @desc This method return all available context names including WebView and NativeApp etc for android
	 * @return contextNames
	 */
	public Set<String> getAllContext() {
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
	public AndroidAction switchContext(String context) {
		try {
			((AndroidDriver)driver).context(context);
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
	public AndroidAction scrollIntoView(Element scrollElement,Element elementToFound,String direction) {
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
	public AndroidAction scrollToEnd(Element scrollElement,String direction) {
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
	
	
	/**
	 * @Description : this method will scroll into view of a particular element with visible text contains
	 * @param element
	 * @param viewOnTop (if true, the top of element will be aligned to the top of visible area)
	 */
	public AndroidAction scrollIntoViewByAndroidUISelectorWithVisibleText(String elementText) {
		try {
			((AndroidDriver)driver).findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\""+elementText+"\"))"));
	} catch (Exception e) {
		e.printStackTrace();
		throw new RuntimeException("Not able to scroll to the element having visible text: "+elementText,e);
	}
		return this;
	}
	
	
	/**
	 * @Description : this method will scroll into view till element is visible.
	 * If you pass direction as SWIPE_UP, page will go down.
	 *  here swipeElement is element by whose size you want to swipe into page and elementToFound is  the element that you want to found
	 * @param swipeElement, direction(up, down, left, right)
	 * @param 
	 */
	public AndroidAction swipeIntoView(Element swipeElement,Element elementToFound,String direction) {
		direction=direction.toLowerCase();
		try {
			for (int i = 0; i < 10; i++) {
				driver.executeScript("mobile: swipeGesture", ImmutableMap.of(
	                    "elementId", ((RemoteWebElement) action.findElement(swipeElement)).getId(),
	                    "direction", direction,
	                    "percent", 1.0
	            ));
				if (action.isElementVisible(elementToFound, 5)) {
					break;
				}
			}	  
	} catch (Exception e) {
		throw new RuntimeException("Not able to swipe to element: "+elementToFound.getElementName(),e);
	}
		return this;
	}
	
	
	/**
	 * Waits for element to be presence, visible clickable and then clicks on it by javascript executor
	 * @param elem
	 * @return
	 */
	public AndroidAction click(Element elem) {
		action.waitForElementPresence(elem);
		action.waitForElementToBeVisible(elem);
		action.waitForElementToBeClickable(elem);
		try {
			driver.executeScript("mobile: clickGesture", ImmutableMap.of(
		              "element", ((RemoteWebElement) action.findElement(elem)).getId()));
		} catch (Exception e) {
			throw new RuntimeException("Not able to click on element "+elem.getElementName(),e);
		}
		return this;
	}
	
	/**
	 * Waits for element to be presence, visible clickable and then long clicks on it by javascript executor
	 * @param elem
	 * @return
	 */
	public AndroidAction longClickGesture(Element elem) {
		action.waitForElementPresence(elem);
		action.waitForElementToBeVisible(elem);
		action.waitForElementToBeClickable(elem);
		try {
			driver.executeScript("mobile: longClickGesture", ImmutableMap.of(
		              "element", ((RemoteWebElement) action.findElement(elem)).getId()));
		} catch (Exception e) {
			throw new RuntimeException("Not able to long click on element "+elem.getElementName(),e);
		}
		return this;
	}
	
	
	/**
	 * Waits for element to be presence, visible clickable and then long clicks on it till given seconds
	 * @param elem, durationInSeconds
	 * @return
	 */
	public AndroidAction longClickGesture(Element elem,int durationInSeconds) {
		action.waitForElementPresence(elem);
		action.waitForElementToBeVisible(elem);
		action.waitForElementToBeClickable(elem);
		try {
			driver.executeScript("mobile: longClickGesture", ImmutableMap.of(
		              "element", ((RemoteWebElement) action.findElement(elem)).getId(),
		              "duration", durationInSeconds*1000
		              ));
		} catch (Exception e) {
			throw new RuntimeException("Not able to long click on element "+elem.getElementName(),e);
		}
		return this;
	}
	
	

	
	/**
	 * @desc  dragElement is element that you want to drag and given 2 coordinates are drop coordinates
	 * @param dragElement
	 * @param endXCordinate
	 * @param endYCordinate
	 * @return
	 */
	public AndroidAction dragAndDrop(Element dragElement, int endXCordinate, int endYCordinate) {
		try {
			driver.executeScript("mobile: dragGesture", ImmutableMap.of(
		              "element", ((RemoteWebElement) action.findElement(dragElement)).getId(),
		              "endX", endXCordinate,
		              "endY", endYCordinate
		              ));
		} catch (Exception e) {
			throw new RuntimeException("Not able to drag and drop the element: "+dragElement.elementName,e);
		}
		return this;
	}
	
	
	/**
	 * @desc  first 2 start coordinates are coordinates of drag and last 2 coordinates are coordinates of drop.
	 * @param startXCordinate
	 * @param startYCordinate
	 * @param endXCordinate
	 * @param endYCordinate
	 * @return
	 */
	public AndroidAction dragAndDrop(int startXCordinate, int startYCordinate, int endXCordinate, int endYCordinate) {
		try {
			driver.executeScript("mobile: dragGesture", ImmutableMap.of(
		              "startX", startXCordinate,
		              "startY", startYCordinate,
		              "endX", endXCordinate,
		              "endY", endYCordinate
			));
		} catch (Exception e) {
			throw new RuntimeException("Not able to drag and drop the element by given cordinates. ",e);
		}
		
		return this;
	}
	
	
	/**
	 * @desc zoom into given element by given percentage-give from 0 to 1
	 * @param elem
	 * @param zoomInPercetage
	 * @return
	 */
	public AndroidAction zoomIn(Element elem, double zoomInPercent) {
		try {
			driver.executeScript("mobile: pinchOpenGesture", ImmutableMap.of(
	                "element", ((RemoteWebElement) action.findElement(elem)).getId(),
	                "percent", zoomInPercent
	            ));
		} catch (Exception e) {
			throw new RuntimeException("Not able to zoom into element "+elem.getElementName() + " by given percentage"+zoomInPercent+". ",e);
		}	
		return this;
	}
	
	
	/**
	 * @desc zoom into given element by default zoom-in percent 1.
	 * @param elem
	 * @return
	 */
	public AndroidAction zoomIn(Element elem) {
		try {
			driver.executeScript("mobile: pinchOpenGesture", ImmutableMap.of(
	                "element", ((RemoteWebElement) action.findElement(elem)).getId(),
	                "percent", 1
	            ));
		} catch (Exception e) {
			throw new RuntimeException("Not able to zoom into element "+elem.getElementName(),e);
		}	
		return this;
	}
	
	
	/**
	 * @desc zoom out from given element by given zoom-out percentage-give from 0 to 1
	 * @param elem
	 * @param zoomOutPercetage
	 * @return
	 */
	public AndroidAction zoomOut(Element elem, double zoomOutPercent) {
		try {
			driver.executeScript("mobile: pinchCloseGesture", ImmutableMap.of(
	                "element", ((RemoteWebElement) action.findElement(elem)).getId(),
	                "percent", zoomOutPercent
	            ));
		} catch (Exception e) {
			throw new RuntimeException("Not able to zoom out from element "+elem.getElementName() + " by given percentage"+zoomOutPercent+". ",e);
		}	
		return this;
	}
	
	
	/**
	 * @desc zoom out from given element by default zoom-out percent 0.
	 * @param elem
	 * @return
	 */
	public AndroidAction zoomOut(Element elem) {
		try {
			driver.executeScript("mobile: pinchCloseGesture", ImmutableMap.of(
	                "element", ((RemoteWebElement) action.findElement(elem)).getId(),
	                "percent", 0
	            ));
		} catch (Exception e) {
			throw new RuntimeException("Not able to zoom out from element "+elem.getElementName(),e);
		}	
		return this;
	}
	
	
	
	/**
	 * @desc zoom into bounding area defined by coordinates with default zoomIn percent 1.
	 * find left point(x), find top point(y), find width point(width point x coordinate - left point x coordinate), 
	 * find height point(height point  y coordinate - top point y coordinate)
	 * left=X, top=Y, width=widthX-leftX, height=hegithY-topY
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 * @return
	 */
	public AndroidAction zoomIn(int left, int top,int width, int height) {
		try {
			driver.executeScript("mobile: pinchOpenGesture", ImmutableMap.of(
					 "left", left,
		             "top", top,
		             "width", width,
		             "height", height,
	                 "percent", 1
	            ));
		} catch (Exception e) {
			throw new RuntimeException("Not able to zoom into bounding area defined by given coordinates. ",e);
		}	
		return this;
	}
	
	
	/**
	 * @desc zoom into bounding area defined by coordinates with given zoom-in percent-give value from 0 to 1
	 * find left point(x), find top point(y), find width point(width point x coordinate - left point x coordinate), 
	 * find height point(height point  y coordinate - top point y coordinate)
	 * left=X, top=Y, width=widthX-leftX, height=hegithY-topY
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 * @param zoomOutPercetage
	 * @return 
	 */
	public AndroidAction zoomIn(int left, int top,int width, int height, double zoomInPercetage) {
		try {
			driver.executeScript("mobile: pinchOpenGesture", ImmutableMap.of(
					 "left", left,
		             "top", top,
		             "width", width,
		             "height", height,
	                 "percent", zoomInPercetage
	            ));
		} catch (Exception e) {
			throw new RuntimeException("Not able to zoom into bounding area defined by given coordinates with given zoom-in percent"+zoomInPercetage+". ",e);
		}	
		return this;
	}

	
	
	/**
	 * @desc zoom out from bounding area defined by coordinates with default zoomOut percent 0.
	 * find left point(x), find top point(y), find width point(width point x coordinate - left point x coordinate), 
	 * find height point(height point  y coordinate - top point y coordinate)
	 * left=X, top=Y, width=widthX-leftX, height=hegithY-topY
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 * @return
	 */
	public AndroidAction zoomOut(int left, int top,int width, int height) {
		try {
			driver.executeScript("mobile: pinchCloseGesture", ImmutableMap.of(
					 "left", left,
		             "top", top,
		             "width", width,
		             "height", height,
	                 "percent", 0
	            ));
		} catch (Exception e) {
			throw new RuntimeException("Not able to zoom out from bounding area defined by given coordinates. ",e);
		}	
		return this;
	}
	
	
	/**
	 * @desc zoom out from bounding area defined by coordinates with given zoom-out percent-give value from 0 to 1
	 * find left point(x), find top point(y), find width point(width point x coordinate - left point x coordinate), 
	 * find height point(height point  y coordinate - top point y coordinate)
	 * left=X, top=Y, width=widthX-leftX, height=hegithY-topY
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 * @param zoomOutPercetage
	 * @return 
	 */
	public AndroidAction zoomOut(int left, int top,int width, int height, double zoomOutPercetage) {
		try {
			driver.executeScript("mobile: pinchCloseGesture", ImmutableMap.of(
					 "left", left,
		             "top", top,
		             "width", width,
		             "height", height,
	                 "percent", zoomOutPercetage
	            ));
		} catch (Exception e) {
			throw new RuntimeException("Not able to zoom out from bounding area defined by given coordinates with given zoom-out percent"+zoomOutPercetage+". ",e);
		}	
		return this;
	}

	
	/**
	 * desc perform android key
	 * @param androidKey
	 * @return
	 */
	public AndroidAction performKeyOperation(AndroidKey androidKey) {
		try {
			((AndroidDriver) driver).pressKey(new KeyEvent().withKey(androidKey));
		} catch (Exception e) {
			throw new RuntimeException("Not able to perform androidKey "+androidKey.name());
		}
		return this;
	}
	
	/**
	 * @desc perform multiple android keys in given sequence
	 * @param androidKeys
	 * @return
	 */
	public AndroidAction performKeyOperations(AndroidKey[] androidKeys) {
		for (AndroidKey androidKey : androidKeys) {
			try {
				((AndroidDriver) driver).pressKey(new KeyEvent().withKey(androidKey));
			} catch (Exception e) {
				throw new RuntimeException("Not able to perform androidKey "+androidKey.name());
			}
		}
		return this;
	}
	
	
	
	/**
	 * @desc hides the keyboard in mobile screen
	 * @return
	 */
	public AndroidAction hideKeyBoard() {
		((AndroidDriver) driver).hideKeyboard();
		return this;
	}
	 

	/**
	 * uninstall app with given app package name
	 * @param appPackageName
	 * @return
	 */
	public AndroidAction uninstallApp(String appPackageName) {
		try {
			boolean appUninstalled=((AndroidDriver) driver).removeApp(appPackageName);
			if (appUninstalled==false) {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new RuntimeException("Not able to uninstall app with app package name "+appPackageName);
		}
		
		return this;
	}
	
		
	/**
	 * @desc installs app with these criteria -> if app update is available update it, if  grant permission flag is true, then grants required permissions to the app, and if installAppInSDCardFlag is true, then it forces app to get installed in SD card
	 * @param appLocation
	 * @param appUpdateFlag
	 * @param grantPermissionFlag
	 * @param installAppInSDCardFlag
	 * @return
	 */
	public AndroidAction installApp(String appLocation, boolean appUpdateFlag,boolean grantPermissionFlag, boolean installAppInSDCardFlag) {
		AndroidInstallApplicationOptions androidInstallApplicationOptions = new AndroidInstallApplicationOptions();
		if (appUpdateFlag) {
			androidInstallApplicationOptions=androidInstallApplicationOptions.withReplaceEnabled();
		}
		if (grantPermissionFlag) {
			androidInstallApplicationOptions=androidInstallApplicationOptions.withGrantPermissionsEnabled();
		}if (installAppInSDCardFlag) {
			androidInstallApplicationOptions=androidInstallApplicationOptions.withUseSdcardEnabled();
		}
		try {
			((AndroidDriver) driver).installApp(appLocation,androidInstallApplicationOptions);
		} catch (Exception e) {
			throw new RuntimeException("Not able to install app which is avaialble at location "+appLocation +" with given criteria");
		}
		return this;
	}
	
	/**
	 * installs app with these criteria -> if app update is available update it, if  grant permission flag is true, then grants required permissions to the app, and if installAppInSDCardFlag is true, then it forces app to get installed in SD card, And wait for app to get installed with given install time out
	 * @param appLocation
	 * @param appUpdateFlag
	 * @param grantPermissionFlag
	 * @param installAppInSDCardFlag
	 * @param installTimeOut
	 * @return
	 */
	public AndroidAction installApp(String appLocation, boolean appUpdateFlag,boolean grantPermissionFlag, boolean installAppInSDCardFlag, int installTimeOut) {
		AndroidInstallApplicationOptions androidInstallApplicationOptions = new AndroidInstallApplicationOptions();
		if (appUpdateFlag) {
			androidInstallApplicationOptions=androidInstallApplicationOptions.withReplaceEnabled();
		}
		if (grantPermissionFlag) {
			androidInstallApplicationOptions=androidInstallApplicationOptions.withGrantPermissionsEnabled();
		}if (installAppInSDCardFlag) {
			androidInstallApplicationOptions=androidInstallApplicationOptions.withUseSdcardEnabled();
		}
		androidInstallApplicationOptions=androidInstallApplicationOptions.withTimeout(Duration.ofSeconds(installTimeOut));
		try {
			((AndroidDriver) driver).installApp(appLocation,androidInstallApplicationOptions);
		} catch (Exception e) {
			throw new RuntimeException("Not able to install app which is avaialble at location "+appLocation +" with given criteria");
		}
		return this;
	}
	
	
	
	
	/**
	 * returns weather app with given package name is installed or not
	 * @param appPackageName
	 * @return
	 */
	public boolean isAppInstalled(String appPackageName) {
		return ((AndroidDriver) driver).isAppInstalled(appPackageName);
	}
	
	/**
	 * @desc runs given app with app package name in background for given time period in seconds
	 * @param appPackageName
	 * @param timeInSeconds
	 * @return
	 */
	public AndroidAction runAppInBackgroudForGivenTimePeriod(String appPackageName, int timeInSeconds) {
		try {
			((AndroidDriver) driver).runAppInBackground(Duration.ofSeconds(timeInSeconds));
		} catch (Exception e) {
			throw new RuntimeException("Not able to run app "+appPackageName+" in background for given time period");
		}
		return this;
	}
	
	/**
	 * @desc activates app or brings app in foreground from background  with given app package name, make sure app should be  running already
	 * @param appPackageName
	 * @return
	 */
	public AndroidAction activateAppOrBringAppInForeground(String appPackageName) {
		try {
			((AndroidDriver) driver).activateApp(appPackageName);
		} catch (Exception e) {
		throw new RuntimeException("Not able to activate app "+appPackageName);
		}
		return this;
	}
	
	
	/**
	 * @desc returns app state like RUNNING_IN_FOREGROUND etc
	 * @param appPackageName
	 * @return
	 */
	public String getAppState(String appPackageName) {
		try {
			 return ((AndroidDriver) driver).queryAppState("io.appium.android.apis").toString();
		} catch (Exception e) {
			throw new RuntimeException("Not able to get the app state");
		}
	}
	
	/**
	 * @desc locks the device
	 * @return
	 */
	public AndroidAction lockDevice() {
		try {
			((AndroidDriver) driver).lockDevice();
		} catch (Exception e) {
			throw new RuntimeException("Not able to lock the device");
		}
		return this;
	}

	
	/**
	 * @desc locks the device for  given time in seconds and then unlocks it
	 * @return
	 */
	public AndroidAction lockDevice(int timeInSeconds) {
		try {
			((AndroidDriver) driver).lockDevice(Duration.ofSeconds(timeInSeconds));
		} catch (Exception e) {
			throw new RuntimeException("Not able to lock the device");
		}
		return this;
	}

	
	/**
	 * @desc returns weather device is locked or not
	 * @return
	 */
	public boolean isDeviceLocked() {
		try {
			return ((AndroidDriver) driver).isDeviceLocked();
		} catch (Exception e) {
			throw new RuntimeException("Not able to check weahter device is locked or not");
		}
	}
	
	
	/**
	 * @desc returns weather device is locked or not, if password/pin/fingerprint set in device, then make sure to pass capabilities accordingly
	 * @return
	 */
	public AndroidAction unlockDevice() {
		try {
			 ((AndroidDriver) driver).unlockDevice();
		} catch (Exception e) {
			throw new RuntimeException("Not able unlock the  device");
		}
		return this;
	}

	

}
