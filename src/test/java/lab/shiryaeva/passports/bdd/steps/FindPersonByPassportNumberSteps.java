package lab.shiryaeva.passports.bdd.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lab.shiryaeva.passports.model.Person;
import lab.shiryaeva.passports.service.PersonService;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

public class FindPersonByPassportNumberSteps {

    @Autowired
    private PersonService personService;

    private String serialNumber;
    private String number;

    private Person person;

    @Given("serial number is {string}")
    public void serialNumberIsSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @And("number is {string}")
    public void numberIsNumber(String number) {
        this.number = number;
    }

    @When("I ask for person with serial number and number as given")
    public void iAskForPersonWithSerialNumberAndNumberAsGiven() {
        person = personService.getPersonByPassportSerialNumber(serialNumber, number);
    }

    @Then("I receive the person")
    public void iReceiveThePerson() {
        Assertions.assertNotNull(person);
    }

    @And("the person's first name is {string}")
    public void thePersonSFirstNameIs(String firstName) {
        Assertions.assertEquals(firstName, person.getFirstName());
    }

    @And("the person's last name is {string}")
    public void thePersonSLastNameIs(String lastName) {
        Assertions.assertEquals(lastName, person.getLastName());
    }

    @And("the person's birth date is {string}")
    public void thePersonSBirthDateIs(String birthDate) {
        Assertions.assertEquals(birthDate, String.valueOf(person.getBirthDate()));
    }
}
