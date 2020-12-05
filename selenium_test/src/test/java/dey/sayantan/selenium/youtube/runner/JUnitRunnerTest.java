package dey.sayantan.selenium.youtube.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java", glue = "dey.sayantan.selenium.youtube.steps", plugin = {
		"html:test-results/youtube-html-report", "json:test-results/youtube.json", "pretty:test-results/youtube-pretty.txt",
		"usage:test-results/youtube-usage.json", "junit:test-results/youtube-results.xml" })
public class JUnitRunnerTest {

}
