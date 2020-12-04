package dey.sayantan.selenium.youtube.steps;

import org.apache.http.util.Asserts;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import dey.sayantan.selenium.youtube.utils.YoutubeTestUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class YoutubeSearchTest {

	private static String CHROME_VERSION = "87.0.4280.88";
	private WebDriver driver;
	WebElement videoToBeClicked;

	@Before
	public void openBrowser() {
		WebDriverManager.chromedriver().version(CHROME_VERSION).setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox");
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
		Asserts.check(driver.getTitle().equals(searchString + " - YouTube"), "Webpage title doesn't match");
	}

	@Then("I see {string} is present in the searchbox of new page")
	public void iSeeQueryIsPresentInTheSearchboxOfNewPage(String searchString) {
		Asserts.check(YoutubeTestUtil.getSearchBoxContent(driver).equals(searchString),
				"Searchbox content is different");
	}

	@Then("I see webpage url is modified with {string}")
	public void iSeeQueryIsModified(String searchString) {
		String url = driver.getCurrentUrl();
		String queryString = url.split("=")[1];
		Asserts.check(queryString.equals(searchString), "Query string is different");
	}

	@After
	public void closeDriver() {
		driver.quit();
	}

}
