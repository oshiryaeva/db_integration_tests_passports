package lab.shiryaeva.passports.testcontainers;

import lab.shiryaeva.passports.PassportsApplication;
import lab.shiryaeva.passports.model.Passport;
import lab.shiryaeva.passports.model.Person;
import lab.shiryaeva.passports.repository.PassportRepository;
import lab.shiryaeva.passports.repository.PersonRepository;
import lab.shiryaeva.passports.service.PassportService;
import lab.shiryaeva.passports.service.PersonService;
import org.flywaydb.core.Flyway;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@Testcontainers
@SpringBootTest(classes = PassportsApplication.class)
@FlywayTest
@Transactional
@AutoConfigureTestEntityManager
@ContextConfiguration(initializers = {DatabaseIntegrationTest.Initializer.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class, TransactionalTestExecutionListener.class})
class DatabaseIntegrationTest {

    @Container
    public static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("passports-test")
            .withUsername("sa")
            .withPassword("sa");

    @Autowired
    private Flyway flyway;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PassportRepository passportRepository;
    @Autowired
    private PassportService passportService;
    @Autowired
    private PersonService personService;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void personRepositoryNotEmpty() {
        Assertions.assertNotNull(personService.getAllPersons());
    }

    @Test
    void passportRepositoryNotEmpty() {
        Assertions.assertNotNull(passportService.getAllPassports());
    }

    @Test
    void personRepositoryCountItems() {
        Assertions.assertEquals(46, personService.getAllPersons().size());
    }

    @Test
    void passportRepositoryCountItems() {
        Assertions.assertEquals(100, passportService.getAllPassports().size());
    }

    @Test
    void shouldAddPersonToRepository() {
        Person candidate = new Person();
        candidate.setId(47L);
        candidate.setFirstName("Sarah");
        candidate.setLastName("Palin");
        candidate.setBirthDate(new java.sql.Date(new java.util.Date().getTime()));
        entityManager.persist(candidate);
        entityManager.flush();
        List<Person> found = personRepository.findByFirstNameAndLastNameAllIgnoreCase(candidate.getFirstName(), candidate.getLastName());
        Assertions.assertEquals(1, found.size());
        Assertions.assertEquals(candidate.getFirstName(), found.get(0).getFirstName());
    }

    @Test
    void shouldUpdatePersonInRepository() {
        Person newbie = new Person();
        newbie.setId(47L);
        newbie.setFirstName("Sarah");
        newbie.setLastName("Palin");
        newbie.setBirthDate(new java.sql.Date(new java.util.Date().getTime()));
        entityManager.persist(newbie);
        entityManager.flush();
        Person toBeUpdated = personRepository.getById(47L);
        toBeUpdated.setFirstName("Sarah Louise");
        entityManager.persist(toBeUpdated);
        entityManager.flush();
        Person found = personRepository.getById(toBeUpdated.getId());
        Assertions.assertEquals(toBeUpdated.getFirstName(), found.getFirstName());
    }

    @Test
    void shouldRemovePersonFromRepository() {
        Person newbie = new Person();
        newbie.setId(47L);
        newbie.setFirstName("Sarah");
        newbie.setLastName("Palin");
        newbie.setBirthDate(new java.sql.Date(new java.util.Date().getTime()));
        final long id = entityManager.persistAndGetId(newbie, Long.class);
        personRepository.deleteById(id);
        entityManager.flush();
        Assertions.assertNull(entityManager.find(Person.class, id));
    }

    @Test
    void shouldFindPassportsByFirstName() {
        String firstName = "Franklin";
        List<Passport> passports = passportService.getPassportsByFirstName(firstName);
        Assertions.assertEquals(4, passports.size());
    }

    @Test
    void shouldFindPassportsByLastName() {
        String lastName = "Eisenhower";
        List<Passport> passports = passportService.getPassportsByLastName(lastName);
        Assertions.assertEquals(2, passports.size());
    }

    @Test
    void shouldFindPassportsByFullName() {
        String firstName = "Barack";
        String lastName = "Obama";
        List<Passport> passports = passportService.getPassportsByFullName(firstName, lastName);
        Assertions.assertEquals(3, passports.size());
        for (Passport passport : passports) {
            Assertions.assertEquals(44, passport.getPerson().getId());
        }
    }

    @Test
    void shouldFindPassportsByYear() {
        Integer birthYear = 1994;
        List<Passport> passports = passportService.getPassportsByBirthYear(birthYear);
        Assertions.assertEquals(10, passports.size());
    }

    @Test
    void shouldFindPassportsByFirstNameAndNulls() {
        String firstName = "Woodrow";
        List<Passport> passports = passportService.getPassportsByWhatever(firstName, null, null);
        Assertions.assertEquals(2, passports.size());
        for (Passport passport : passports) {
            Assertions.assertEquals(28, passport.getPerson().getId());
        }
    }

    @Test
    void shouldFindPassportsByLastNameAndNulls() {
        String lastName = "Roosevelt";
        List<Passport> passports = passportService.getPassportsByWhatever(null, lastName, null);
        Assertions.assertEquals(4, passports.size());
    }

    @Test
    void shouldFindPassportsByBirthYearAndNulls() {
        Integer birthYear = 1994;
        List<Passport> passports = passportService.getPassportsByWhatever(null, null, birthYear);
        Assertions.assertEquals(10, passports.size());
    }

    @Test
    void shouldFindPersonsByNumber() {
        String number = "636372";
        List<Person> persons = personService.getPersonByPassportNumber(number);
        Assertions.assertEquals(1, persons.size());
        Assertions.assertEquals(16, persons.get(0).getId());
        Assertions.assertEquals("Abraham", persons.get(0).getFirstName());
        Assertions.assertEquals("Lincoln", persons.get(0).getLastName());
    }

    @Test
    void shouldFindPersonsByFullPassportNumber() {
        String serialNumber = "4449";
        String number = "500998";
        Person persons = personService.getPersonByPassportSerialNumber(serialNumber, number);
        Assertions.assertEquals(40, persons.getId());
        Assertions.assertEquals("Ronald", persons.getFirstName());
        Assertions.assertEquals("Reagan", persons.getLastName());
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

}