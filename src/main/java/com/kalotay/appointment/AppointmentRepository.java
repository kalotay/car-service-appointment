package com.kalotay.appointment;

import java.util.Optional;

public interface AppointmentRepository {
  Appointment create(Appointment appointment);
  Optional<Appointment> fetch(String id);
}
