package lab.shiryaeva.passports.api;

import lab.shiryaeva.passports.model.Passport;
import lab.shiryaeva.passports.model.Person;
import lab.shiryaeva.passports.repository.PassportRepository;
import lab.shiryaeva.passports.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class PassportsAPI {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PassportRepository passportRepository;

    @GetMapping(value = "/persons", produces = "application/json")
    public ResponseEntity<List<Person>> getAllPersons() {
        try {
            List<Person> personList = personRepository.findAll();
            return new ResponseEntity<>(personList, HttpStatus.OK);
        } catch (Exception e) {
            log.debug("PassportsPersonAPI.getAllPersons(): " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(value = "/number/{number}", produces = "application/json")
    public ResponseEntity<Person> getPersonByPassportNumber(@PathVariable String number) {
        try {
            Person person = personRepository.findByPassports_Number(Integer.valueOf(number));
            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (Exception e) {
            log.debug("PassportsPersonAPI.getPersonByPassportNumber(): " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(value = "/serialNumber/{serialNumber}/number/{number}", produces = "application/json")
    public ResponseEntity<Person> getPersonByPassportSerialNumber(@PathVariable String serialNumber, @PathVariable String number) {
        try {
            Person person = personRepository.findByPassports_SerialNumberAndPassports_Number(Integer.valueOf(serialNumber), Integer.valueOf(number));
            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (Exception e) {
            log.debug("PassportsPersonAPI.getPersonByPassportSerialNumber(): " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(value = "/lastName/{lastName}", produces = "application/json")
    public ResponseEntity<List<Passport>> getPassportsByLastName(@PathVariable @NotBlank String lastName) {
        try {
            List<Passport> passportList = passportRepository.findByPerson_LastNameAllIgnoreCase(lastName);
            return new ResponseEntity<>(passportList, HttpStatus.OK);
        } catch (Exception e) {
            log.debug("PassportsPersonAPI.getPassportsByLastName(): " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(value = "/firstName/{firstName}", produces = "application/json")
    public ResponseEntity<List<Passport>> getPassportsByFirstName(@PathVariable @NotBlank String firstName) {
        try {
            List<Passport> passportList = passportRepository.findByPerson_FirstNameAllIgnoreCase(firstName);
            return new ResponseEntity<>(passportList, HttpStatus.OK);
        } catch (Exception e) {
            log.debug("PassportsPersonAPI.getPassportsByLastName(): " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(value = "/birthYear/{birthYear}", produces = "application/json")
    public ResponseEntity<List<Passport>> getPassportByBirthYear(@PathVariable @NotBlank @Size(min = 4, max = 4) Integer birthYear) {
        try {
            List<Passport> passportList = passportRepository.findByPerson_BirthDate(birthYear);
            return new ResponseEntity<>(passportList, HttpStatus.OK);
        } catch (Exception e) {
            log.debug("PassportsPersonAPI.getPassportsByLastName(): " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(value = {"/search/", "/search"}, produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Passport>> searchWithParams(@RequestParam(name = "firstName", required = false) String firstName,
                                                           @RequestParam(name = "lastName", required = false) String lastName,
                                                           @RequestParam(name = "birthYear", required = false) Integer birthYear) {
        List<Passport> passports = new ArrayList<>();;

        if (firstName != null && lastName != null && birthYear != null) {
            passports = passportRepository.findByPerson_FirstNameAndPerson_LastNameAndPerson_BirthDateAllIgnoreCase(firstName, lastName, birthYear);
        }
        if (firstName == null && lastName != null && birthYear != null) {
            passports = passportRepository.findByPerson_LastNameAndPerson_BirthDateAllIgnoreCase(lastName, birthYear);
        }
        if (firstName == null && lastName == null && birthYear != null) {
            passports = passportRepository.findByPerson_BirthDate(birthYear);
        }
        if (firstName != null && lastName == null && birthYear != null) {
            passports = passportRepository.findByPerson_FirstNameAndPerson_BirthDateAllIgnoreCase(firstName, birthYear);
        }
        if (firstName != null && lastName != null && birthYear == null) {
            passports = passportRepository.findByPerson_FirstNameAndPerson_LastNameAllIgnoreCase(firstName, lastName);
        }
        if (firstName != null && lastName == null && birthYear == null ) {
            passports = passportRepository.findByPerson_FirstNameAllIgnoreCase(firstName);
        }
        if (firstName == null && lastName != null && birthYear == null ) {
            passports = passportRepository.findByPerson_LastNameAllIgnoreCase(lastName);
        }

        return new ResponseEntity<>(passports, HttpStatus.OK);
    }
}
