package dey.sayantan.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class YoutubeTestUtil {

	public static String URL = "https://www.youtube.com";

	private static String CONTENT_CONTAINER_ID = "contents";
	private static String VIDEO_TAG = "ytd-rich-item-renderer";
	private static String SEARCH_BOX_TAG = "ytd-searchbox";
	private static final String FORM = "form";

	public static void clickOnVideoByIndex(int index, WebDriver driver) {
		if (index <= 0)
			index = 1;
		openYoutubeIfNotOpened(driver);
		WebElement homeVideosContainer = driver.findElement(By.id(CONTENT_CONTAINER_ID));
		WebElement videoToBeClicked = homeVideosContainer.findElements(By.tagName(VIDEO_TAG)).get(index - 1);
		Actions action = new Actions(driver);
		action.moveToElement(videoToBeClicked);
		videoToBeClicked.click();

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

	private static void openYoutubeIfNotOpened(WebDriver driver) {
		if (!driver.getCurrentUrl().equals(URL))
			driver.get(URL);
	}

}
