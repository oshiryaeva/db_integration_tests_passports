package lab.shiryaeva.passports.dbunit;

import lab.shiryaeva.passports.PassportsApplication;
import lab.shiryaeva.passports.repository.Person2PassportRepository;
import lab.shiryaeva.passports.service.PassportService;
import lab.shiryaeva.passports.service.PersonService;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(classes = PassportsApplication.class)
@FlywayTest
@ContextConfiguration(initializers = {DBUnitTriggerTest.Initializer.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        FlywayTestExecutionListener.class})
public class DBUnitTriggerTest {

    @Container
    static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("passports-test")
            .withUsername("postgres")
            .withPassword("password");
    @Autowired
    private PersonService personService;
    @Autowired
    private PassportService passportService;
    @Autowired
    private Person2PassportRepository person2PassportRepository;

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
    public void shouldRemovePersonFromDb() throws Exception {
        IDatabaseTester tester = new JdbcDatabaseTester(postgreSQLContainer.getDriverClassName(),
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword());
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("dataset/person.xml"));
        String[] ignore = {"id", "birth_date"};
        IDataSet actualData = tester.getConnection().createDataSet();
        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "person", ignore);
    }

    @Test
    @FlywayTest
    public void shouldRemovePersonAndLinkedPassportsFromDb() throws Exception {
        IDatabaseTester tester = new JdbcDatabaseTester(postgreSQLContainer.getDriverClassName(),
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword());
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("dataset/passport_no_roosevelt.xml"));
        String[] ignore = {"id", "issue_date", "expiration_date"};
        personService.delete(26);
        IDataSet actualData = tester.getConnection().createDataSet();
        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "passport", ignore);
    }

    // DBUnit cannot treat View as Table directly
    @Test
    @FlywayTest
    public void shouldUpdateViewAfterPersonDelete() throws Exception {
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("dataset/view_no_biden.xml"));
        personService.delete(46);
        assertEquals(expectedData.getTable("view").getRowCount(), person2PassportRepository.findAll().size());
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