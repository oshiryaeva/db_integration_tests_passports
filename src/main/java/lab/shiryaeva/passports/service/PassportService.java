package lab.shiryaeva.passports.service;

import lab.shiryaeva.passports.model.Passport;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PassportService {

    List<Passport> getAllPassports();

    List<Passport> getPassportsByLastName(String lastName);

    List<Passport> getPassportsByFirstName(String firstName);

    List<Passport> getPassportsByFullName(String firstName, String lastName);

    List<Passport> getPassportsByBirthYear(Integer birthYear);

    List<Passport> getPassportsByWhatever(@Nullable String firstName, @Nullable String lastName, @Nullable Integer birthDate);

}
