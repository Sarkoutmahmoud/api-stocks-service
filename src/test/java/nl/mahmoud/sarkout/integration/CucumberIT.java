package nl.mahmoud.sarkout.integration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/features"}, plugin = {"html:target/testreport", "json:target/cucumber.json"}, strict = true)
public class CucumberIT {

}