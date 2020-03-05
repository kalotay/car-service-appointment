package com.kalotay.appointment;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppointmentController {

  private final AppointmentRepository appointmentRepository;

  public AppointmentController(@Autowired AppointmentRepository appointmentRepository) {
    this.appointmentRepository = appointmentRepository;
  }

  @PostMapping("/appointment")
  public Appointment create(@RequestBody Appointment appointment) {
    return appointmentRepository.create(appointment);
  }

  @GetMapping("/appointment/{id}")
  public Appointment fetch(@PathVariable long id) {
    return appointmentRepository.fetch(id).orElseThrow(AppointmentNotFoundException::new);
  }

  @DeleteMapping("/appointment/{id}")
  public void delete(@PathVariable long id) {
    appointmentRepository.delete(id);
  }

  @PutMapping("/appointment/{id}")
  public Appointment update(@PathVariable long id, @RequestBody Appointment appointment) {
    return appointmentRepository.update(id, appointment).orElseThrow(AppointmentNotFoundException::new);
  }

  @GetMapping("/appointment")
  public List<Appointment> query(
      @RequestParam("from") String from,
      @RequestParam("to") String to) {
    LocalDateTime fromTime;
    LocalDateTime toTime;
    try {
      fromTime = LocalDateTime.parse(from);
      toTime = LocalDateTime.parse(to);
    } catch (DateTimeParseException e) {
      throw new BadQueryParametersException(e);
    }
    return appointmentRepository.query(fromTime, toTime);
  }
}
