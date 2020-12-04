package dey.sayantan.selenium.youtube;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.http.util.Asserts;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import dey.sayantan.selenium.utils.YoutubeTestUtil;
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

	@Given("I have youtube homepage open")
	public void i_have_youtube_homepage_open() {
		WebDriverManager.chromedriver().version(CHROME_VERSION).setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		YoutubeTestUtil.openYoutubeIfNotOpened(driver);
	}

	@When("I choose a video")
	public void i_choose_a_video() {
		// Choosing part is mocked here by generating a random number that is the index
		// of the video on homepage
		videoToBeClicked = YoutubeTestUtil.selectVideoToBeClickedByIndex(ThreadLocalRandom.current().nextInt(1, 22),
				driver);
	}

	@When("open it in a new tab")
	public void open_it_in_a_new_tab() {
		new Actions(driver).keyDown(Keys.CONTROL).click(videoToBeClicked).keyUp(Keys.CONTROL).build().perform();
	}

	@When("switch to that tab")
	public void switch_to_that_tab() {
		String currentHandle = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(currentHandle))
				driver.switchTo().window(handle);
		}
	}

	@Then("i can see video page opened")
	public void i_can_see_video_page_opened() {
		Asserts.check(driver.getCurrentUrl().matches(YoutubeTestUtil.OPENED_VIDEO_REGEX),
				"URL of the video opened is wrong :" + driver.getCurrentUrl());
	}

	@Then("video starts playing")
	public void video_starts_playing() {
		Asserts.check(YoutubeTestUtil.durationOfCurrentVideoPlayed(driver) != 0, "video is not played");
		driver.quit();
	}
}
