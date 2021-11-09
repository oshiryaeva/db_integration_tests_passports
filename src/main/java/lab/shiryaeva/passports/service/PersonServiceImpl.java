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
    public void save(Person person) {
        personRepository.save(person);
    }

    @Override
    public void delete(long id) {
        personRepository.deleteById(id);
    }
}
