package com.kalotay.appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
  Appointment create(Appointment appointment);
  Optional<Appointment> fetch(long id);
  void delete(long id);
  Optional<Appointment> update(long id, Appointment appointment);
  List<Appointment> query(LocalDateTime from, LocalDateTime to);
}
