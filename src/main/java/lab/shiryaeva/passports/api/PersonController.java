package lab.shiryaeva.passports.api;

import lab.shiryaeva.passports.model.Person;
import lab.shiryaeva.passports.service.PassportService;
import lab.shiryaeva.passports.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/person")
public class PersonController {

    @Autowired
    PassportService passportService;
    @Autowired
    private PersonService personService;

    @GetMapping(value = "/number/{number}", produces = "application/json")
    public ResponseEntity<List<Person>> getPersonsByPassportNumber(@PathVariable String number) {
        List<Person> persons = personService.getPersonByPassportNumber(number);
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping(value = "/serialNumber/{serialNumber}/number/{number}", produces = "application/json")
    public ResponseEntity<Person> getPersonByPassportSerialNumber(@PathVariable String serialNumber, @PathVariable String number) {
        Person person = personService.getPersonByPassportSerialNumber(serialNumber, number);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping(value = "/lastName/{lastName}")
    public ResponseEntity<Person> getPerson(@PathVariable String lastName) {
        log.debug("Get person: {}", lastName);
        if (!personService.getPersonByLastName(lastName).isEmpty()) {
            List<Person> personList = personService.getPersonByLastName(lastName);
            return new ResponseEntity<>(personList.get(0), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/persons")
    public List<Person> getAll() {
        return personService.getAllPersons();
    }

    @GetMapping(value = "/get/{id}")
    public Person getOne(@PathVariable Long id) {
        return personService.get(id);
    }

    @PostMapping(value = "/post")
    public Person createPerson(@RequestBody Person newPerson) {
        return personService.save(newPerson);
    }

    @PutMapping(value = "/put/{id}")
    public Person updatePerson(@RequestBody Person person, @PathVariable Long id) {
        return personService.updateById(id, person);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.delete(id);
    }
}

