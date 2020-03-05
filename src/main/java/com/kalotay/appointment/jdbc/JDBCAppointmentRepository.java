package com.kalotay.appointment.jdbc;

import com.kalotay.appointment.Appointment;
import com.kalotay.appointment.AppointmentRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JDBCAppointmentRepository implements AppointmentRepository {

  private final CrudAppointmentRepository crudAppointmentRepository;

  public JDBCAppointmentRepository(
      @Autowired CrudAppointmentRepository crudAppointmentRepository) {
    this.crudAppointmentRepository = crudAppointmentRepository;
  }

  @Override
  public Appointment create(Appointment appointment) {
    return crudAppointmentRepository.save(appointment);
  }

  @Override
  public Optional<Appointment> fetch(long id) {
    return crudAppointmentRepository.findById(id);
  }

  @Override
  public void delete(long id) {
    crudAppointmentRepository.deleteById(id);
  }

  @Override
  public Optional<Appointment> update(long id, Appointment appointment) {
    return Optional.ofNullable(crudAppointmentRepository.save(new Appointment(id, appointment.getAppointmentTime(), appointment.getPrice(), appointment.getDetails())));
  }

  @Override
  public List<Appointment> query(LocalDateTime from, LocalDateTime to) {
    return crudAppointmentRepository.query(from, to);
  }
}
