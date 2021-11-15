package lab.shiryaeva.passports.repository;

import lab.shiryaeva.passports.model.Person2Passport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Person2PassportRepository extends JpaRepository<Person2Passport, Integer> {
}