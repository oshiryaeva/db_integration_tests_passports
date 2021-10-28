package lab.shiryaeva.passports;

import lab.shiryaeva.passports.model.Passport;
import lab.shiryaeva.passports.model.Person;
import lab.shiryaeva.passports.repository.PassportRepository;
import lab.shiryaeva.passports.repository.PersonRepository;
import org.junit.ClassRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;

import java.util.List;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Testcontainers
@Transactional
@ContextConfiguration(initializers = {RepositoryIntegrationTest.Initializer.class})
public class RepositoryIntegrationTest {

    @ClassRule
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withInitScript("init.sql")
            .withUsername("sa")
            .withPassword("sa");


    private static void startContainers() {
        Startables.deepStart(Stream.of(postgreSQLContainer)).join();
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            startContainers();
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Autowired
    private PassportRepository passportRepository;

    @Autowired
    private PersonRepository personRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Test
    public void contextLoads() {
        Assertions.assertNotNull(personRepository);
        Assertions.assertNotNull(passportRepository);
    }

/*    @BeforeAll
    @Sql("init.sql")
    static void runInitScript() {

    }*/

    @Test
    @Transactional(readOnly = true)
    void shouldGetAllPersons() {
        List<Person> persons = personRepository.findAll();
        persons.forEach(person -> log.info("person: {}", person));
        Assertions.assertEquals(46, persons.size());
    }

    @Test
    @Transactional(readOnly = true)
    void shouldGetAllPassports() {
        List<Passport> passports = passportRepository.findAll();
        passports.forEach(passport -> log.info("passport: {}", passport));
        Assertions.assertEquals(100, passports.size());
    }


}