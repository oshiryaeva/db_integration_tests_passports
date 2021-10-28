package lab.shiryaeva.passports.repository;

import lab.shiryaeva.passports.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PassportRepository extends JpaRepository<Passport, Long> {

    List<Passport> findByPerson_FirstNameAllIgnoreCase(String firstName);

    List<Passport> findByPerson_FirstNameAndPerson_LastNameAllIgnoreCase(String firstName, String lastName);

    List<Passport> findByPerson_LastNameAllIgnoreCase(String lastName);

    @Query("select p from Passport p where year(p.person.birthDate) = ?1")
    List<Passport> findByPerson_BirthDate(Integer birthYear);

    @Query("select p from Passport p where p.person.firstName = ?1 and p.person.lastName = ?2 and year(p.person.birthDate) = ?3")
    List<Passport> findByPerson_FirstNameAndPerson_LastNameAndPerson_BirthDateAllIgnoreCase(String firstName, String lastName, Integer birthYear);

    @Query("select p from Passport p where p.person.lastName = ?1 and year(p.person.birthDate) = ?2")
    List<Passport> findByPerson_LastNameAndPerson_BirthDateAllIgnoreCase(String lastName, Integer birthDate);

    @Query("select p from Passport p where p.person.firstName = ?1 and year(p.person.birthDate) = ?2")
    List<Passport> findByPerson_FirstNameAndPerson_BirthDateAllIgnoreCase(String firstName, Integer birthDate);

}