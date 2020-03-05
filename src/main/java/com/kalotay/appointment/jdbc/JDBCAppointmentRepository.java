package com.kalotay.appointment.jdbc;

import com.kalotay.appointment.Appointment;
import com.kalotay.appointment.AppointmentRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

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
  public Optional<Appointment> fetch(Long id) {
    return crudAppointmentRepository.findById(id);
  }
}
