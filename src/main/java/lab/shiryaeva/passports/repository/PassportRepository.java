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

    @Query("select p from Passport p where upper(p.person.firstName) = upper(?1) or upper(p.person.lastName) = upper(?2) or year(p.person.birthDate) = upper(?3)")
    List<Passport> findByPerson_FirstNameOrPerson_LastNameOrPerson_BirthDateAllIgnoreCase(@Nullable String firstName, @Nullable String lastName, @Nullable Integer birthDate);

    @Query("select p from Passport p where year(p.person.birthDate) = ?1")
    List<Passport> findByPerson_BirthDate(Integer birthDate);
}