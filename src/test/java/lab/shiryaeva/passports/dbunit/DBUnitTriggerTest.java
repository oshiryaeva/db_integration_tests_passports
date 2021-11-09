package lab.shiryaeva.passports.dbunit;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lab.shiryaeva.passports.PassportsApplication;
import lab.shiryaeva.passports.model.Passport;
import lab.shiryaeva.passports.model.Person;
import lab.shiryaeva.passports.model.Person2Passport;
import lab.shiryaeva.passports.model.Person2PassportRepository;
import lab.shiryaeva.passports.service.PassportService;
import lab.shiryaeva.passports.service.PersonService;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
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

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(classes = PassportsApplication.class)
@FlywayTest
@Transactional
@AutoConfigureTestEntityManager
@ContextConfiguration(initializers = {DBUnitTriggerTest.Initializer.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        FlywayTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class DBUnitTriggerTest {

    @Autowired
    private PersonService personService;
    @Autowired
    private PassportService passportService;
    @Autowired
    private Person2PassportRepository person2PassportRepository;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("passports-test")
            .withUsername("postgres")
            .withPassword("password");

    @Test
    void repositoriesNotEmpty() {
        Assertions.assertNotNull(personService.getAllPersons());
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
    @FlywayTest
    public void shouldRemovePersonAndLinkedPassportsFromDb() throws Exception {
        List<Person> personList = this.personService.getAllPersons();
        List<Passport> passportList = this.passportService.getAllPassports();
        assertEquals("Theodore", personList.get(25).getFirstName());
        assertEquals("Roosevelt", personList.get(25).getLastName());
        assertEquals(4, passportService.getPassportsByLastName("Roosevelt").size());
        personService.delete(26);
        assertEquals(45, personService.getAllPersons().size());
        assertEquals(96, passportService.getAllPassports().size());
        assertTrue(passportService.getPassportsByLastName("Roosevelt").isEmpty());
    }

    @Test
    @FlywayTest
    public void shouldUpdateViewAfterPersonDelete() throws Exception {
        List<Person2Passport> viewBefore = this.person2PassportRepository.findAll();
        assertEquals(46, viewBefore.size());
        personService.delete(43);
        List<Person2Passport> viewAfter = this.person2PassportRepository.findAll();
        assertEquals(45, viewAfter.size());
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