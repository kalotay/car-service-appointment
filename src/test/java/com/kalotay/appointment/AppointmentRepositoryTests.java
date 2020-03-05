package com.kalotay.appointment;

import static com.kalotay.appointment.Helpers.getAppointmentTime;
import static com.kalotay.appointment.Helpers.verifyBetweenDateRange;
import static com.kalotay.appointment.Helpers.verifySortedByPrice;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.time.LocalDateTime;
import java.util.List;
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
    LocalDateTime appointmentTime = getAppointmentTime();
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
    Optional<Appointment> appointment = repository.fetch(123L);
    assertThat(appointment.isEmpty(), equalTo(true));
  }

  @Test
  void createdAppointmentCanBeFetched() {
    LocalDateTime appointmentTime = getAppointmentTime();
    double price = 52.3;
    String details = "oil change";
    Appointment created = repository.create(new Appointment(null, appointmentTime, price, details));
    Long id = created.getId();
    Optional<Appointment> fetch = repository.fetch(id);
    Appointment fetched = fetch.orElseThrow();
    assertThat(created.getAppointmentTime(), equalTo(fetched.getAppointmentTime()));
    assertThat(created.getPrice(), equalTo(fetched.getPrice()));
    assertThat(created.getDetails(), equalTo(fetched.getDetails()));
  }

  @Test
  void appointmentCanBeDeleted() {
    LocalDateTime appointmentTime = getAppointmentTime();
    double price = 52.3;
    String details = "oil change";
    Appointment created = repository.create(new Appointment(null, appointmentTime, price, details));
    Long id = created.getId();
    repository.delete(id);
    Optional<Appointment> appointment = repository.fetch(id);
    assertThat(appointment.isEmpty(), equalTo(true));
  }

  @Test
  void appointmentCanBeUpdated() {
    LocalDateTime originalTime = getAppointmentTime();
    double originalPrice = 52.3;
    String originalDetails = "oil change";
    Appointment created = repository.create(new Appointment(null, originalTime, originalPrice, originalDetails));
    Long id = created.getId();

    LocalDateTime updatedTime = getAppointmentTime();
    double updatedPrice = 2.0;
    String updatedDetails = "just a look";
    repository.update(id, new Appointment(null, updatedTime, updatedPrice, updatedDetails));
    Appointment appointment = repository.fetch(id).orElseThrow();
    assertThat(appointment.getId(), equalTo(id));
    assertThat(appointment.getAppointmentTime(), equalTo(updatedTime));
    assertThat(appointment.getPrice(), equalTo(updatedPrice));
    assertThat(appointment.getDetails(), equalTo(updatedDetails));
  }

  @Test
  void appointmentsCanBeQueried() {
    LocalDateTime baseTime = getAppointmentTime();

    for (int i = 0; i < 5; i += 1) {
      Appointment appointment = new Appointment(null, baseTime.plusHours(i - 2), 100 - 10*i, Integer.toHexString(i));
      repository.create(appointment);
    }

    LocalDateTime to = baseTime.plusMinutes(90);
    LocalDateTime from = baseTime.minusMinutes(90);
    List<Appointment> appointments = repository.query(from, to);
    assertThat(appointments, verifyBetweenDateRange(from, to));
    assertThat(appointments, verifySortedByPrice());
  }

}
