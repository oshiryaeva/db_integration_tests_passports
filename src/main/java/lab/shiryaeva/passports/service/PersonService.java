package lab.shiryaeva.passports.service;

import lab.shiryaeva.passports.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {

    List<Person> getAllPersons();

    List<Person> getPersonByPassportNumber(String number);

    Person getPersonByPassportSerialNumber(String serialNumber, String number);

}
