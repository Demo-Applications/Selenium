package dey.sayantan.selenium.youtube.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java", glue = "dey.sayantan.selenium.youtube.steps", plugin = {
		"html:target/youtube-html-report", "json:target/youtube.json", "pretty:target/youtube-pretty.txt",
		"usage:target/youtube-usage.json", "junit:target/youtube-results.xml" })
public class JUnitRunner {

}
