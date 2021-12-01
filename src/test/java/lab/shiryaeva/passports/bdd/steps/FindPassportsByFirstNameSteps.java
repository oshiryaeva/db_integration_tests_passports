package lab.shiryaeva.passports.bdd.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lab.shiryaeva.passports.model.Passport;
import lab.shiryaeva.passports.service.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FindPassportsByFirstNameSteps {

    @Autowired
    private PassportService passportService;

    private List<Passport> passports;
    private String firstName;
    private ResponseEntity<List<Passport>> latestResponse;

    @Given("firstName is {string}")
    public void firstnameIs(String firstName) {
        this.firstName = firstName;
    }

    @When("I ask for passports with first name as given")
    public void iAskForPassportsWithFirstNameAsGiven() {
        passports = passportService.getPassportsByFirstName(firstName);
    }

    @Then("I receive the list of passports")
    public void iReceiveTheListOfPassports() {
        assertNotNull(passports);
    }

    @And("The list size equals {int}")
    public void theListSizeEqualsNumber(int number) {
        assertEquals(number, passports.size());
    }

}
