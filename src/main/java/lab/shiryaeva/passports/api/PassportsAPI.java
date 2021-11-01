package lab.shiryaeva.passports.api;

import lab.shiryaeva.passports.model.Passport;
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
@RequestMapping(value = "/")
public class PassportsAPI {

    @Autowired
    private PassportService passportService;

    @Autowired
    private PersonService personService;


    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<Passport>> getAllPassports() {
        return new ResponseEntity<>(passportService.getAllPassports(), HttpStatus.OK);
    }

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

    @GetMapping(value = "/firstName/{firstName}", produces = "application/json")
    public ResponseEntity<List<Passport>> getPassportsByFirstName(@PathVariable String firstName) {
        List<Passport> passports = passportService.getPassportsByFirstName(firstName);
        return new ResponseEntity<>(passports, HttpStatus.OK);
    }


    @GetMapping(value = "/lastName/{lastName}", produces = "application/json")
    public ResponseEntity<List<Passport>> getPassportsByLastName(@PathVariable String lastName) {
        List<Passport> passports = passportService.getPassportsByLastName(lastName);
        return new ResponseEntity<>(passports, HttpStatus.OK);
    }

    @GetMapping(value = "/firstName/{firstName}/lastName/{lastName}", produces = "application/json")
    public ResponseEntity<List<Passport>> getPassportsByFullName(@PathVariable String firstName, @PathVariable String lastName) {
        List<Passport> passports = passportService.getPassportsByFullName(firstName, lastName);
        return new ResponseEntity<>(passports, HttpStatus.OK);
    }

    @GetMapping(value = "/birthYear/{birthYear}", produces = "application/json")
    public ResponseEntity<List<Passport>> getPassportsByBirthYear(@PathVariable Integer birthYear) {
        List<Passport> passports = passportService.getPassportsByBirthYear(birthYear);
        return new ResponseEntity<>(passports, HttpStatus.OK);
    }

    @PostMapping(value = "/{firstName}/{lastName}/{birthYear}", produces = "application/json")
    public ResponseEntity<List<Passport>> getPassportsByWhatever(@PathVariable String firstName, @PathVariable String lastName, @PathVariable Integer birthYear) {
        List<Passport> passports = passportService.getPassportsByWhatever(firstName, lastName, birthYear);
        return new ResponseEntity<>(passports, HttpStatus.OK);
    }

}
