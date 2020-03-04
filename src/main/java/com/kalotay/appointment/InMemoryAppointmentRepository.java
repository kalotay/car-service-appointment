package com.kalotay.appointment;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Service;

@Service
public class InMemoryAppointmentRepository implements AppointmentRepository {

  private final ConcurrentMap<String, Appointment> store = new ConcurrentHashMap<>();

  @Override
  public Appointment create(Appointment appointment) {
    String id = UUID.randomUUID().toString();
    Appointment toStore = new Appointment(id, appointment.getAppointmentTime(),
        appointment.getPrice(), appointment.getDetails());
    store.put(id, toStore);
    return toStore;
  }

  @Override
  public Optional<Appointment> fetch(String id) {
    return Optional.ofNullable(store.get(id));
  }
}
