package com.kalotay.appointment;

public class InMemoryAppointmentRepositoryTests extends AppointmentRepositoryTests {

  @Override
  protected AppointmentRepository createRepository() {
    return new InMemoryAppointmentRepository();
  }
}
