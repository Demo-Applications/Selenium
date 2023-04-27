package dey.sayantan.selenium.youtube.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.html.HTMLFormElement;

public class YoutubeTestUtil {

	public static final String URL = "https://www.youtube.com/";
	public static final String OPENED_VIDEO_REGEX = "^http(s)?:\\/\\/(www|m).youtube.com\\/(watch\\?v=|embed\\/|v|shorts|)(.*?((?=[&#?])|$))";

	private static final String CONTAINER_GRID_XPATH = "//ytd-rich-grid-renderer/div[@id='contents']";
	private static final String VIDEO_TAG = "ytd-rich-item-renderer";
	private static final String SEARCH_BOX_TAG = "ytd-searchbox";
	private static final String FORM = "form";
	private static final String PROGRESS_BAR_CLASS = "ytp-progress-bar";
	private static final String SHORTS_TAG = "ytd-reel-video-renderer";
	private static final String SHORTS_PROGRESS_BAR_CLASS = "progress-bar-line";
	private static final String PROGRESS_ATTRIBUTE = "aria-valuenow";
	private static final String VIDEO_AD_CLASS = "ytp-ad-player-overlay";
	private static final String TITLE_XPATH = "//title";
	private static final String SEARCHBOX_XPATH = "//input[@id='search']";

	public static void clickOnVideoByIndex(int index, WebDriver driver) {
		if (index <= 0)
			index = 1;
		WebElement videoToBeClicked = selectVideoToBeClickedByIndex(index, driver);
		Actions action = new Actions(driver);
		action.moveToElement(videoToBeClicked);
		videoToBeClicked.click();

	}

	public static WebElement selectVideoToBeClickedByIndex(int index, WebDriver driver) {
		openYoutubeIfNotOpened(driver);
		WebElement homeVideosContainer = driver.findElement(By.xpath(CONTAINER_GRID_XPATH));
		WebElement videoToBeClicked = selectVideoFromContainerByindex(index, homeVideosContainer, driver);
		Actions action = new Actions(driver);
		action.moveToElement(videoToBeClicked);
		return videoToBeClicked;
	}

	private static WebElement selectVideoFromContainerByindex(int index, WebElement homeVideosContainer,
			WebDriver driver) {
		while (homeVideosContainer.findElements(By.tagName(VIDEO_TAG)).size() < index) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0, window.scrollY + 200)");
		}
		return homeVideosContainer.findElements(By.tagName(VIDEO_TAG)).get(index - 1);
	}

	public static void searchForVideo(String videoSearchString, WebDriver driver) {
		if (StringUtils.isBlank(videoSearchString))
			throw new RuntimeException("Search string is empty");
		openYoutubeIfNotOpened(driver);
		WebElement searchContainer = driver.findElement(By.tagName(SEARCH_BOX_TAG));
		searchContainer.click();
		searchContainer.sendKeys(videoSearchString);
		WebElement formElement = searchContainer.findElement(By.tagName(FORM));
		formElement.submit();
		new WebDriverWait(driver, 10).until(ExpectedConditions.stalenessOf(formElement));

	}

	public static void openYoutubeIfNotOpened(WebDriver driver) {
		if (!driver.getCurrentUrl().equals(URL))
			driver.get(URL);
	}

	/** return number of seconds the current video is played */
	public static int durationOfCurrentVideoPlayed(WebDriver driver) {
		if (!driver.getCurrentUrl().matches(OPENED_VIDEO_REGEX))
			throw new RuntimeException("No video is opened in this page");
		if (!driver.findElements(By.className(VIDEO_AD_CLASS)).isEmpty()) {
			try {
				WebElement skipAdEl = driver.findElement(By.className("ytp-ad-skip-button"));
				// Wait till skip button
				new WebDriverWait(driver, 40).until(ExpectedConditions.elementToBeClickable(skipAdEl));
				skipAdEl.click();
			} catch (TimeoutException e) {
				// No skip Ad button
				driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			}
		}
		List<WebElement> youtubeShorts = driver.findElements(By.tagName(SHORTS_TAG));
		if (!youtubeShorts.isEmpty()) {
			WebElement progressBarElement = youtubeShorts.get(0).findElement(By.className(SHORTS_PROGRESS_BAR_CLASS));
			return Integer.parseInt(progressBarElement.getAttribute(PROGRESS_ATTRIBUTE));
		} else {
			WebElement progressBarElement = driver.findElement(By.className(PROGRESS_BAR_CLASS));
			return Integer.parseInt(progressBarElement.getAttribute(PROGRESS_ATTRIBUTE));
		}
	}

	public static String getPageTitle(WebDriver driver) {
		return driver.findElement(By.xpath(TITLE_XPATH)).getText();
	}

	public static Object getSearchBoxContent(WebDriver driver) {
		return driver.findElement(By.xpath(SEARCHBOX_XPATH)).getAttribute("value");
	}

	public static void openNewTab(WebDriver driver) {
		driver.findElement(By.tagName("body")).sendKeys(Keys.CONTROL + "t");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1)); // switches to new tab
	}

}
