package lab.shiryaeva.passports.service;

import lab.shiryaeva.passports.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {

    List<Person> getAllPersons();

    List<Person> getPersonByPassportNumber(String number);

    Person getPersonByPassportSerialNumber(String serialNumber, String number);

    Person save(Person person);

    void delete(long id);

    List<Person> getPersonByLastName(String lastName);

    Person get(Long id);

    Person updateById(Long id, Person person);
}
