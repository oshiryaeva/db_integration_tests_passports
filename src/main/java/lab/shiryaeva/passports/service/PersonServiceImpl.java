package lab.shiryaeva.passports.service;

import lab.shiryaeva.passports.model.Person;
import lab.shiryaeva.passports.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public List<Person> getPersonByPassportNumber(String number) {
        return personRepository.findByPassports_Number(Integer.valueOf(number));
    }

    @Override
    public Person getPersonByPassportSerialNumber(String serialNumber, String number) {
        return personRepository.findByPassports_SerialNumberAndPassports_Number(Integer.valueOf(serialNumber), Integer.valueOf(number));
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void delete(long id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<Person> getPersonByLastName(String lastName) {
        return personRepository.findByLastName(lastName);
    }

    @Override
    public Person get(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find person " + id));
    }

    @Override
    public Person updateById(Long id, Person newPerson) {
        return personRepository.findById(id)
                .map(person -> {
                    person.setFirstName(newPerson.getFirstName());
                    person.setLastName(newPerson.getLastName());
                    person.setBirthDate(newPerson.getBirthDate());
                    return personRepository.save(person);
                })
                .orElseGet(() -> {
                    newPerson.setId(id);
                    return personRepository.save(newPerson);
                });
    }
}
