package dey.sayantan.selenium.youtube.steps;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import dey.sayantan.selenium.youtube.utils.YoutubeTestUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

/** Step definitions for selecting on a random video on homepage */
//For time being test is coupled with Chrome and Windows 
public class YoutubeHomeTest {
	private WebDriver driver;
	WebElement videoToBeClicked;

	@Before("@video-click")
	public void openBrowser() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox");
		options.addArguments("--incognito");
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
		String pageUrl = driver.getCurrentUrl();
		assertTrue("URL of the video opened is wrong :" + pageUrl, pageUrl.matches(YoutubeTestUtil.OPENED_VIDEO_REGEX));
	}

	@Then("video starts playing")
	public void videoStartsPlaying() {
		assertNotEquals("video is not played", 0, YoutubeTestUtil.durationOfCurrentVideoPlayed(driver));
	}

	@After("@video-click")
	public void concludeTestSuite(Scenario scenario) throws IOException {
		if (scenario.isFailed()) {
			// Create the output directory if it doesn't exist
			Path outputDir = Paths.get("target", "output");
			Files.createDirectories(outputDir);

			// Take the screenshot and save it to a file
			TakesScreenshot ts = (TakesScreenshot) driver;
			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			;
			Path screenshotPath = outputDir.resolve(scenario.getName() + ".png");
			Files.copy(screenshotFile.toPath(), screenshotPath);

		}
		driver.close();
		driver.quit();
	}
}
