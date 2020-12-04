package dey.sayantan.selenium.youtube;

import org.apache.http.util.Asserts;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import dey.sayantan.selenium.utils.YoutubeTestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class YoutubeSearchTest {

	private static String CHROME_VERSION = "87.0.4280.88";
	private WebDriver driver;
	WebElement videoToBeClicked;

	@Given("I have youtube web page open")
	public void i_have_youtube_web_page_open() {
		if (driver != null)
			return;
		WebDriverManager.chromedriver().version(CHROME_VERSION).setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		YoutubeTestUtil.openYoutubeIfNotOpened(driver);
	}

	@When("I search for this {string}")
	public void i_search_for_this_query(String searchString) {
		YoutubeTestUtil.searchForVideo(searchString, driver);
	}

	@Then("I verify the {string} in page title")
	public void i_verify_the_query_in_page_title(String searchString) {
		Asserts.check(driver.getTitle().equals(searchString + " - YouTube"), "Webpage title doesn't match");
	}

	@Then("I see {string} is present in the searchbox of new page")
	public void i_see_query_is_present_in_the_searchbox_of_new_page(String searchString) {
		Asserts.check(YoutubeTestUtil.getSearchBoxContent(driver).equals(searchString),
				"Searchbox content is different");
	}

	@Then("I see webpage url is modified with {string}")
	public void i_see_query_is_modified(String searchString) {
		String url = driver.getCurrentUrl();
		String queryString = url.split("=")[1];
		Asserts.check(queryString.equals(searchString), "Query string is different");

	}

}
