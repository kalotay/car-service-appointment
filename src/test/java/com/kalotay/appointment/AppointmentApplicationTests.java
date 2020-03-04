package com.kalotay.appointment;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsNot.not;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AppointmentApplicationTests {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void appointmentCanBePosted() {
    LocalDateTime appointmentTime = LocalDateTime.now();
    double price = 52.3;
    String details = "oil change";

    webTestClient.post()
        .uri("/appointment")
        .bodyValue(new Appointment(null, appointmentTime, price, details))
        .exchange()
        .expectStatus().isOk()
        .expectBody(Appointment.class)
        .value(a -> a.getId(), not(nullValue()))
        .value(a -> a.getAppointmentTime(), equalTo(appointmentTime))
        .value(a -> a.getPrice(), equalTo(price))
        .value(a -> a.getDetails(), equalTo(details));
  }

  @Test
  void appointmentCanFetched() {
    LocalDateTime appointmentTime = LocalDateTime.now();
    double price = 52.3;
    String details = "oil change";

    Appointment appointment = webTestClient.post()
        .uri("/appointment")
        .bodyValue(new Appointment(null, appointmentTime, price, details))
        .exchange()
        .expectStatus().isOk()
        .returnResult(Appointment.class).getResponseBody().blockFirst();

    String id = appointment.getId();
    webTestClient.get()
        .uri("/appointment/{id}", id)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Appointment.class)
        .value(a -> a.getId(), equalTo(id))
        .value(a -> a.getAppointmentTime(), equalTo(appointmentTime))
        .value(a -> a.getPrice(), equalTo(price))
        .value(a -> a.getDetails(), equalTo(details));
  }


}
