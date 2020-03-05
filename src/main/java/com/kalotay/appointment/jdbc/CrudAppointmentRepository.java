package com.kalotay.appointment.jdbc;

import com.kalotay.appointment.Appointment;
import org.springframework.data.repository.CrudRepository;

public interface CrudAppointmentRepository extends CrudRepository<Appointment, Long> {
}
