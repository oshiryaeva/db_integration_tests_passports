package lab.shiryaeva.passports.repository;

import lab.shiryaeva.passports.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.util.List;

public interface PassportRepository extends JpaRepository<Passport, Long> {

    List<Passport> findByPerson_LastNameIgnoreCase(String lastName);

    List<Passport> findByPerson_FirstNameIgnoreCase(String firstName);

    List<Passport> findByPerson_FirstNameAndPerson_LastNameAllIgnoreCase(String firstName, String lastName);

    @Query("select p from Passport p where p.person.firstName = ?1 or p.person.lastName = ?2 or year(p.person.birthDate) = ?3")
    List<Passport> findByPerson_FirstNameOrPerson_LastNameOrPerson_BirthDate(@Nullable String firstName, @Nullable String lastName, @Nullable Integer birthDate);

    @Query("select p from Passport p where year(p.person.birthDate) = ?1")
    List<Passport> findByPerson_BirthDate(Integer birthDate);

    List<Passport> findByPerson_FirstNameLikeIgnoreCase(String firstName);

    List<Passport> findByPerson_LastNameLikeIgnoreCase(String lastName);

    List<Passport> findByPerson_FirstNameLikeAndPerson_LastNameLikeAllIgnoreCase(String firstName, String lastName);




}