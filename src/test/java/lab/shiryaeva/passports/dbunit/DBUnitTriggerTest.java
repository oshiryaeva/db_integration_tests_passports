package lab.shiryaeva.passports.dbunit;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lab.shiryaeva.passports.PassportsApplication;
import lab.shiryaeva.passports.model.Person;
import lab.shiryaeva.passports.repository.Person2PassportRepository;
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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(classes = PassportsApplication.class)
@FlywayTest
@AutoConfigureTestEntityManager
@ContextConfiguration(initializers = {DBUnitTriggerTest.Initializer.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        FlywayTestExecutionListener.class})
public class DBUnitTriggerTest {

    @Autowired
    private PersonService personService;
    @Autowired
    private PassportService passportService;
    @Autowired
    private Person2PassportRepository person2PassportRepository;
    @Autowired
    private EntityManager entityManager;

    @Container
    public static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
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
    @DatabaseSetup("person.xml")
    @ExpectedDatabase("person_no_roosevelt.xml")
    public void shouldRemovePersonFromDb() {
        List<Person> personList = this.personService.getAllPersons();
        assertEquals("Theodore", personList.get(25).getFirstName());
        assertEquals("Roosevelt", personList.get(25).getLastName());
        personService.delete(26);
        assertEquals(45, personService.getAllPersons().size());
    }

    @Test
    @FlywayTest
    @DatabaseSetup("passport.xml")
    @ExpectedDatabase("passport_no_roosevelt.xml")
    public void shouldRemovePersonAndLinkedPassportsFromDb() {
        personService.delete(26);
        assertEquals(45, personService.getAllPersons().size());
        assertEquals(96, passportService.getAllPassports().size());
    }

    @Test
    @FlywayTest
    @Modifying
    @Query(value = "DELETE FROM person WHERE person.id=43", nativeQuery = true)
    @DatabaseSetup("view.xml")
    @ExpectedDatabase("view_no_bush.xml")
    public void shouldUpdateViewAfterPersonDelete() {
        personService.delete(43);
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