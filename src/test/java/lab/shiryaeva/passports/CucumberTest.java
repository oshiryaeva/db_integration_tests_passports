package lab.shiryaeva.passports;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        tags = "not @ignore",
        glue = {"lab.shiryaeva.passports.bdd.steps", "lab.shiryaeva.passports.bdd.tooling"},
        plugin = {"pretty", "html:target/cucumber"},
        monochrome = true
)
public class CucumberTest {
}
