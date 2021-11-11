package lab.shiryaeva.passports.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;


public interface Person2PassportRepository extends ViewRepository<Person2Passport, Integer> {
}