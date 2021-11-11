package lab.shiryaeva.passports.repository;

import lab.shiryaeva.passports.model.Person;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);

    List<Person> findByPassports_Number(Integer number);

    Person findByPassports_SerialNumberAndPassports_Number(Integer serialNumber, Integer number);
}