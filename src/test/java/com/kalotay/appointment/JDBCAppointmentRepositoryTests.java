package com.kalotay.appointment;

import com.kalotay.appointment.jdbc.CrudAppointmentRepository;
import com.kalotay.appointment.jdbc.JDBCAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@ContextConfiguration(initializers = {JDBCAppointmentRepositoryTests.Initializer.class})
public class JDBCAppointmentRepositoryTests extends AppointmentRepositoryTests {

  private static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
      .withDatabaseName("appointment")
      .withUsername("sa")
      .withPassword("sa");

  //see: https://www.baeldung.com/spring-boot-testcontainers-integration-test
  static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      postgreSQLContainer.start();
      String jdbcUrl = postgreSQLContainer.getJdbcUrl();
      TestPropertyValues.of("spring.datasource.url=" + jdbcUrl,
          "spring.datasource.username=" + postgreSQLContainer.getUsername(),
          "spring.datasource.password=" + postgreSQLContainer.getPassword()).applyTo(applicationContext);
    }
  }

  @Autowired
  CrudAppointmentRepository crudAppointmentRepository;

  @Override
  protected AppointmentRepository createRepository() {
    return new JDBCAppointmentRepository(crudAppointmentRepository);
  }
}
