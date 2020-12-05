package dey.sayantan.selenium.youtube.steps;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.http.util.Asserts;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import dey.sayantan.selenium.youtube.utils.YoutubeTestUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

/** Step definitions for selecting on a random video on homepage */
//For time being test is coupled with Chrome and Windows 
public class YoutubeHomeTest {
	private static String CHROME_VERSION = "87.0.4280.88";
	private WebDriver driver;
	WebElement videoToBeClicked;

	@Before("@video-click")
	public void openBrowser() {
		WebDriverManager.chromedriver().version(CHROME_VERSION).setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
	}

	@Given("I have youtube homepage open")
	public void iHaveYoutubeHomepageOpen() {
		YoutubeTestUtil.openYoutubeIfNotOpened(driver);
	}

	@When("I choose a video")
	public void iChooseAVideo() {
		// Choosing part is mocked here by generating a random number that is the index
		// of the video on homepage
		videoToBeClicked = YoutubeTestUtil.selectVideoToBeClickedByIndex(ThreadLocalRandom.current().nextInt(1, 14),
				driver);
	}

	@When("open it in a new tab")
	public void openItInANewTab() {
		new Actions(driver).keyDown(Keys.CONTROL).click(videoToBeClicked).keyUp(Keys.CONTROL).build().perform();
	}

	@When("switch to that tab")
	public void switchToThatTab() {
		String currentHandle = driver.getWindowHandle();
		driver.close();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(currentHandle))
				driver.switchTo().window(handle);
		}
	}

	@Then("i can see video page opened")
	public void iCanSeeVideoPageOpened() {
		Asserts.check(driver.getCurrentUrl().matches(YoutubeTestUtil.OPENED_VIDEO_REGEX),
				"URL of the video opened is wrong :" + driver.getCurrentUrl());
	}

	@Then("video starts playing")
	public void videoStartsPlaying() {
		Asserts.check(YoutubeTestUtil.durationOfCurrentVideoPlayed(driver) != 0, "video is not played");
	}

	@After("@video-click")
	public void closeDriver() {
		driver.quit();
	}
}
