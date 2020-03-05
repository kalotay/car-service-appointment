package com.kalotay.appointment.jdbc;

import com.kalotay.appointment.Appointment;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CrudAppointmentRepository extends CrudRepository<Appointment, Long> {

  @Query("SELECT * FROM appointment where appointment_time > :from AND appointment_time < :to ORDER BY price")
  List<Appointment> query(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}
