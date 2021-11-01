package lab.shiryaeva.passports.service;

import lab.shiryaeva.passports.model.Passport;
import lab.shiryaeva.passports.repository.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassportServiceImpl implements PassportService {

    @Autowired
    private PassportRepository passportRepository;

    @Override
    public List<Passport> getAllPassports() {
        return passportRepository.findAll();
    }

    @Override
    public List<Passport> getPassportsByLastName(String lastName) {
        return passportRepository.findByPerson_LastNameLikeIgnoreCase(lastName);
    }

    @Override
    public List<Passport> getPassportsByFirstName(String firstName) {
        return passportRepository.findByPerson_FirstNameLikeIgnoreCase(firstName);
    }

    @Override
    public List<Passport> getPassportsByFullName(String firstName, String lastName) {
        return passportRepository.findByPerson_FirstNameLikeAndPerson_LastNameLikeAllIgnoreCase(firstName, lastName);
    }

    @Override
    public List<Passport> getPassportsByBirthYear(Integer birthYear) {
        return passportRepository.findByPerson_BirthDate(birthYear);
    }

    @Override
    public List<Passport> getPassportsByWhatever(@Nullable String firstName, @Nullable String lastName, @Nullable Integer birthDate) {
        return passportRepository.findByPerson_FirstNameOrPerson_LastNameOrPerson_BirthDate(firstName, lastName, birthDate);
    }
}
