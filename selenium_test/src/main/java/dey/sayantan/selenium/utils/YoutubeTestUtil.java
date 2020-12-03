package dey.sayantan.selenium.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class YoutubeTestUtil {

	public static final String URL = "https://www.youtube.com/";
	public static final String OPENED_VIDEO_REGEX = "^http(s)?:\\/\\/(www|m).youtube.com\\/watch\\?v=.+$";

	private static final String CONTENT_CONTAINER_ID = "contents";
	private static final String VIDEO_TAG = "ytd-rich-item-renderer";
	private static final String SEARCH_BOX_TAG = "ytd-searchbox";
	private static final String FORM = "form";
	private static final String PROGRESS_BAR_CLASS = "ytp-progress-bar";
	private static final String PROGRESS_ATTRIBUTE = "aria-valuenow";
	private static final String VIDEO_AD_CLASS = "ytp-ad-player-overlay";

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
		WebElement homeVideosContainer = driver.findElement(By.id(CONTENT_CONTAINER_ID));
		WebElement videoToBeClicked = selectVideoFromContainerByindex(index, homeVideosContainer, driver);
		return videoToBeClicked;
	}

	private static WebElement selectVideoFromContainerByindex(int index, WebElement homeVideosContainer,
			WebDriver driver) {
		while (homeVideosContainer.findElements(By.tagName(VIDEO_TAG)).size() < index) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0, window.scrollY + 100)");
			try {
				driver.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return homeVideosContainer.findElements(By.tagName(VIDEO_TAG)).get(index - 1);
	}

	public static void searchForVideo(String videoSearchString, WebDriver driver) {
		if (videoSearchString.isBlank())
			throw new RuntimeException("Search string is empty");
		openYoutubeIfNotOpened(driver);
		WebElement searchContainer = driver.findElement(By.tagName(SEARCH_BOX_TAG));
		searchContainer.click();
		searchContainer.sendKeys(videoSearchString);
		searchContainer.findElement(By.tagName(FORM)).submit();

	}

	public static void openYoutubeIfNotOpened(WebDriver driver) {
		if (!driver.getCurrentUrl().equals(URL))
			driver.get(URL);
	}

	/** return number of seconds the current video is played */
	public static int durationOfCurrentVideoPlayed(WebDriver driver) {
		if (!driver.getCurrentUrl().matches(OPENED_VIDEO_REGEX))
			throw new RuntimeException("No video is opened in this page");
		try {
			driver.findElement(By.className(VIDEO_AD_CLASS));
			driver.findElement(By.className("ytp-ad-skip-button")).click();
		} catch (NoSuchElementException ex) {
		}
		WebElement progressBarElement = driver.findElement(By.className(PROGRESS_BAR_CLASS));
		return Integer.parseInt(progressBarElement.getAttribute(PROGRESS_ATTRIBUTE));
	}

}
