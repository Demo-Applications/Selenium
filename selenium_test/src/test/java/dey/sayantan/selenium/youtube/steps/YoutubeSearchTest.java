package dey.sayantan.selenium.youtube.steps;

import static org.junit.Assert.assertEquals;

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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class YoutubeSearchTest {

	private WebDriver driver;
	WebElement videoToBeClicked;

	@Before("@video-search")
	public void openBrowser() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox");
		options.addArguments("--incognito");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
	}

	@Given("I have youtube web page open")
	public void iHaveYoutubeWebPageOpen() {
		YoutubeTestUtil.openYoutubeIfNotOpened(driver);
	}

	@When("I search for this {string}")
	public void iSearchForThisQuery(String searchString) {
		YoutubeTestUtil.searchForVideo(searchString, driver);
	}

	@Then("I verify the {string} in page title")
	public void iVerifyTheQueryInPageTitle(String searchString) {
		assertEquals("Webpage title doesn't match", searchString + " - YouTube", driver.getTitle());
	}

	@Then("I see {string} is present in the searchbox of new page")
	public void iSeeQueryIsPresentInTheSearchboxOfNewPage(String searchString) {
		assertEquals("Searchbox content is different", searchString, YoutubeTestUtil.getSearchBoxContent(driver));
	}

	@Then("I see webpage url is modified with {string}")
	public void iSeeQueryIsModified(String searchString) {
		String url = driver.getCurrentUrl();
		String queryString = url.split("=")[1];
		assertEquals("Query string is different", searchString, queryString);
	}

	@After("@video-search")
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
