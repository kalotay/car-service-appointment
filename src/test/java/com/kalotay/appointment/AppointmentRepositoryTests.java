package com.kalotay.appointment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class AppointmentRepositoryTests {

  protected abstract AppointmentRepository createRepository();

  private AppointmentRepository repository;

  @BeforeAll
  void setupRepository() {
    repository = createRepository();
  }

  @Test
  void appointmentCanBeCreated() {
    LocalDateTime appointmentTime = LocalDateTime.now();
    double price = 52.3;
    String details = "oil change";
    Appointment toCreate = new Appointment(null, appointmentTime, price, details);
    Appointment created = repository.create(toCreate);
    assertThat(created.getId(), not(nullValue()));
    assertThat(created.getAppointmentTime(), equalTo(appointmentTime));
    assertThat(created.getPrice(), equalTo(price));
    assertThat(created.getDetails(), equalTo(details));
  }

  @Test
  void twoDifferentAppointmentsHaveDifferentIds() {
    Appointment firstAppointment = repository.create(
        new Appointment(null, LocalDateTime.now(), 52.3, "oil change"));
    Appointment secondAppointment = repository.create(
        new Appointment(null, LocalDateTime.now().plusHours(3), 1000, "defective gearbox"));
    assertThat(firstAppointment.getId(), not(equalTo(secondAppointment.getId())));
  }

  @Test
  void fetchReturnsEmptyForUnknownId() {
    Optional<Appointment> appointment = repository.fetch("foo");
    assertThat(appointment.isEmpty(), equalTo(true));
  }

  @Test
  void createdAppointmentCanBeFetched() {
    LocalDateTime appointmentTime = LocalDateTime.now();
    double price = 52.3;
    String details = "oil change";
    Appointment created = repository.create(new Appointment(null, appointmentTime, price, details));
    String id = created.getId();
    Appointment fetched = repository.fetch(id).orElseThrow();
    assertThat(created.getAppointmentTime(), equalTo(fetched.getAppointmentTime()));
    assertThat(created.getPrice(), equalTo(fetched.getPrice()));
    assertThat(created.getDetails(), equalTo(fetched.getDetails()));
  }

}
