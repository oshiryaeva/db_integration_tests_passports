package lab.shiryaeva.passports.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources", glue = {"lab.shiryaeva.passports"})
public class CucumberIntegrationTest {

}
