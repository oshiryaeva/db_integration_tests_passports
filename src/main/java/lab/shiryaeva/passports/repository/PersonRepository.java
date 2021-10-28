package lab.shiryaeva.passports.repository;

import lab.shiryaeva.passports.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByPassports_Number(Integer number);

    Person findByPassports_SerialNumberAndPassports_Number(Integer serialNumber, Integer number);

}