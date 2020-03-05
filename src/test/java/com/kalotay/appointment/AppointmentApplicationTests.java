package com.kalotay.appointment;

import static com.kalotay.appointment.Helpers.getAppointmentTime;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsNot.not;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {JDBCAppointmentRepositoryTests.Initializer.class})
class AppointmentApplicationTests {

  private static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
      .withDatabaseName("appointment")
      .withUsername("sa")
      .withPassword("sa");

  //see: https://www.baeldung.com/spring-boot-testcontainers-integration-test
  static class Initializer implements
      ApplicationContextInitializer<ConfigurableApplicationContext> {
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
  private WebTestClient webTestClient;

  @Test
  void appointmentCanBePosted() {
    LocalDateTime appointmentTime = getAppointmentTime();
    double price = 52.3;
    String details = "oil change";

    webTestClient.post()
        .uri("/appointment")
        .bodyValue(new Appointment(null, appointmentTime, price, details))
        .exchange()
        .expectStatus().isOk()
        .expectBody(Appointment.class)
        .value(Appointment::getId, not(nullValue()))
        .value(Appointment::getAppointmentTime, equalTo(appointmentTime))
        .value(Appointment::getPrice, equalTo(price))
        .value(Appointment::getDetails, equalTo(details));
  }

  @Test
  void appointmentCanFetched() {
    LocalDateTime appointmentTime = getAppointmentTime();
    double price = 52.3;
    String details = "oil change";

    Appointment appointment = webTestClient.post()
        .uri("/appointment")
        .bodyValue(new Appointment(null, appointmentTime, price, details))
        .exchange()
        .expectStatus().isOk()
        .returnResult(Appointment.class).getResponseBody().blockFirst();

    Long id = appointment.getId();
    webTestClient.get()
        .uri("/appointment/{id}", id)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Appointment.class)
        .value(Appointment::getId, equalTo(id))
        .value(Appointment::getAppointmentTime, equalTo(appointmentTime))
        .value(Appointment::getPrice, equalTo(price))
        .value(Appointment::getDetails, equalTo(details));
  }

  @Test
  void appointmentCanBeDeleted() {
    LocalDateTime appointmentTime = getAppointmentTime();
    double price = 52.3;
    String details = "oil change";

    Appointment appointment = webTestClient.post()
        .uri("/appointment")
        .bodyValue(new Appointment(null, appointmentTime, price, details))
        .exchange()
        .expectStatus().isOk()
        .returnResult(Appointment.class).getResponseBody().blockFirst();

    Long id = appointment.getId();

    webTestClient.get()
        .uri("/appointment/{id}", id)
        .exchange()
        .expectStatus().isOk();

    webTestClient.delete()
        .uri("/appointment/{id}", id)
        .exchange()
        .expectStatus().isOk();

    webTestClient.get()
        .uri("/appointment/{id}", id)
        .exchange()
        .expectStatus().isNotFound();
  }

  @Test
  void appointmentCanUpdated() {
    LocalDateTime originalAppointmentTime = getAppointmentTime();
    double originalPrice = 52.3;
    String originalDetails = "oil change";

    Appointment originalAppointment = webTestClient.post()
        .uri("/appointment")
        .bodyValue(new Appointment(null, originalAppointmentTime, originalPrice, originalDetails))
        .exchange()
        .expectStatus().isOk()
        .returnResult(Appointment.class).getResponseBody().blockFirst();

    Long id = originalAppointment.getId();

    LocalDateTime updatedAppointmentTime = getAppointmentTime();
    double updatedPrice = 91.1;
    String updatedDetails = "oil change + lick of paint";
    webTestClient.put()
        .uri("/appointment/{id}", id)
        .bodyValue(new Appointment(null, updatedAppointmentTime, updatedPrice, updatedDetails))
        .exchange()
        .expectBody(Appointment.class)
        .value(Appointment::getId, equalTo(id))
        .value(Appointment::getAppointmentTime, equalTo(updatedAppointmentTime))
        .value(Appointment::getPrice, equalTo(updatedPrice))
        .value(Appointment::getDetails, equalTo(updatedDetails));

    webTestClient.get()
        .uri("/appointment/{id}", id)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Appointment.class)
        .value(Appointment::getId, equalTo(id))
        .value(Appointment::getAppointmentTime, equalTo(updatedAppointmentTime))
        .value(Appointment::getPrice, equalTo(updatedPrice))
        .value(Appointment::getDetails, equalTo(updatedDetails));

  }

  @Test
  void appointmentsCanBeQueried() {
    LocalDateTime baseTime = getAppointmentTime();

    for (int i = 0; i < 5; i += 1) {
      Appointment appointment = new Appointment(null, baseTime.plusHours(i - 2), 100 - 10*i, Integer.toHexString(i));
      webTestClient.post()
          .uri("/appointment")
          .bodyValue(appointment)
          .exchange()
          .expectStatus().isOk()
          .returnResult(Appointment.class);
    }

    LocalDateTime to = baseTime.plusMinutes(90);
    LocalDateTime from = baseTime.minusMinutes(90);
    webTestClient.get()
        .uri("/appointment?to={to}&from={from}", to, from)
        .exchange()
        .expectStatus().isOk()
        .expectBody(new ParameterizedTypeReference<List<Appointment>>() {})
        .value(Helpers.verifyBetweenDateRange(from, to))
        .value(Helpers.verifySortedByPrice());
  }

}
