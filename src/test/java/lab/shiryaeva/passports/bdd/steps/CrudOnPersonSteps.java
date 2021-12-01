package lab.shiryaeva.passports.bdd.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lab.shiryaeva.passports.model.Person;
import lab.shiryaeva.passports.service.PassportService;
import lab.shiryaeva.passports.service.PersonService;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class CrudOnPersonSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PersonService personService;

    @Autowired
    private PassportService passportService;

    private ResponseEntity<Person> responseEntity;

    private String baseUri;

    @Given("http baseUri is {string}")
    public void httpBaseUriIs(String uri) {
        baseUri = uri;
    }

    @When("I POST a person")
    public void iPOSTAPerson(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            responseEntity = tryToCreatePerson(columns.get("id"), columns.get("firstName"), columns.get("lastName"), columns.get("birthDate"));
        }
    }

    @Then("http response code should be {int}")
    public void httpResponseCodeShouldBe(int code) {
        Assertions.assertEquals(code, responseEntity.getStatusCodeValue());
    }

    @When("I GET a person with id {int}")
    public void iGETAPersonWithId(int id) {
        responseEntity = new ResponseEntity<>(personService.get((long) id), HttpStatus.OK);
    }

    @And("http response body should contain first name {string}")
    public void httpResponseBodyShouldContainFirstName(String firstName) {
        Assertions.assertEquals(firstName, responseEntity.getBody().getFirstName());
    }

    @And("http response body should contain last name {string}")
    public void httpResponseBodyShouldContainLastName(String lastName) {
        Assertions.assertEquals(lastName, responseEntity.getBody().getLastName());
    }

    @And("http response body should contain birth date {string}")
    public void httpResponseBodyShouldContainBirthDate(String birthDate) {
        Assertions.assertEquals(birthDate, String.valueOf(responseEntity.getBody().getBirthDate()));
    }


    @When("I PUT a person")
    public void iPUTAPerson(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            responseEntity = tryToUpdatePerson(Long.parseLong(columns.get("id")), columns.get("lastName"));
        }
    }

    @And("person with id {int} should contain first name {string}")
    public void personWithIdShouldContainFirstName(int id, String firstName) {
        Assertions.assertEquals(firstName, personService.get((long) id).getFirstName());
    }

    @And("person with id {int} should contain last name {string}")
    public void personWithIdShouldContainLastName(int id, String lastName) {
        Assertions.assertEquals(lastName, personService.get((long) id).getLastName());
    }

    @When("I DELETE a person with {int}")
    public void iDELETEAPersonWith(int id) {
        personService.delete(id);
        responseEntity = new ResponseEntity<>(HttpStatus.OK);

    }

    @And("person with id {int} should not be found")
    public void personWithIdShouldNotBeFound(int id) {
        Assertions.assertNull(personService.get((long) id));
    }

    private ResponseEntity<Person> tryToUpdatePerson(long id, String lastName) {
        Person person = personService.get(id);
        person.setLastName(lastName);
        personService.updateById(id, person);
        responseEntity = new ResponseEntity<Person>(person, HttpStatus.ACCEPTED);
        return responseEntity;
    }


    // produces code 500 so needs to be improved
/*    private ResponseEntity<Person> tryToCreatePerson(String firstName, String lastName, String birthDate) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setBirthDate(java.sql.Date.valueOf(birthDate));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> httpEntity = new HttpEntity<Person>(person, headers);
        ResponseEntity<Person> response;
        try {
            response = restTemplate.postForEntity(BASE_URI+"/post", httpEntity, Person.class);
        } catch (RestClientException e) {
            if (e.getCause() instanceof ConnectException) {
                return new ResponseEntity<Person>(HttpStatus.CONFLICT);
            }
            throw e;
        }
        return response;
    }*/

    private ResponseEntity<Person> tryToCreatePerson(String id, String firstName, String lastName, String birthDate) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setBirthDate(java.sql.Date.valueOf(birthDate));
        if (!personService.getAllPersons().contains(person)) {
            person.setId(Long.valueOf(id));
            personService.save(person);
        }
        responseEntity = new ResponseEntity<>(person, HttpStatus.CREATED);
        return responseEntity;
    }


}
