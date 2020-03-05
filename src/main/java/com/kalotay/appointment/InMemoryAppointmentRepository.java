package com.kalotay.appointment;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class InMemoryAppointmentRepository implements AppointmentRepository {

  AtomicLong counter = new AtomicLong();

  private final ConcurrentMap<Long, Appointment> store = new ConcurrentHashMap<>();

  @Override
  public Appointment create(Appointment appointment) {
    long id = counter.incrementAndGet();
    Appointment toStore = new Appointment(id, appointment.getAppointmentTime(),
        appointment.getPrice(), appointment.getDetails());
    store.put(id, toStore);
    return toStore;
  }

  @Override
  public Optional<Appointment> fetch(Long id) {
    return Optional.ofNullable(store.get(id));
  }
}
