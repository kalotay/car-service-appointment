package com.kalotay.appointment;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

public class InMemoryAppointmentRepository implements AppointmentRepository {

  private final AtomicLong counter = new AtomicLong();

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
  public Optional<Appointment> fetch(long id) {
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public void delete(long id) {
    store.remove(id);
  }

  @Override
  public Optional<Appointment> update(long id, Appointment appointment) {
    return Optional.ofNullable(store.computeIfPresent(id, (i, unused) ->
        new Appointment(i, appointment.getAppointmentTime(), appointment.getPrice(), appointment.getDetails())));
  }

  @Override
  public List<Appointment> query(LocalDateTime from, LocalDateTime to) {
    return store.values().stream()
        .filter(a -> a.getAppointmentTime().isBefore(to) && a.getAppointmentTime().isAfter(from))
        .sorted(Comparator.comparingDouble(Appointment::getPrice))
        .collect(Collectors.toList());
  }
}
