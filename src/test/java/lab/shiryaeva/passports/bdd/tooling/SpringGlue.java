package lab.shiryaeva.passports.bdd.tooling;

import io.cucumber.spring.CucumberContextConfiguration;
import lab.shiryaeva.passports.PassportsApplication;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {PassportsApplication.class})
public class SpringGlue {

}
