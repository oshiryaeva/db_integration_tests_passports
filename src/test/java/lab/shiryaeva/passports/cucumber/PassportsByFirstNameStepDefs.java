package lab.shiryaeva.passports.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.junit.Cucumber;
import lab.shiryaeva.passports.model.Passport;
import lab.shiryaeva.passports.service.PassportService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;

import static org.junit.Assert.assertEquals;


public class PassportsByFirstNameStepDefs {

    @Autowired
    private PassportService passportService;

    private List<Passport> passports;
    private String firstName;
    private ResponseEntity<List<Passport>> latestResponse;

    @Given("^firstName is Franklin$")
    public void firstnameIsFranklin() {
        firstName = "Franklin";
    }

    @When("^I ask for Franklin's passports$")
    public void iAskForFranklinSPassports() {
        passports = passportService.getPassportsByFirstName(firstName);
    }

    @Then("^I should get (\\d+)$")
    public void iShouldGet(int arg0) {
        assert passports != null;
        assertEquals(4, passports.size());
        assertEquals(HttpStatus.OK, latestResponse.getStatusCode());
    }
}
