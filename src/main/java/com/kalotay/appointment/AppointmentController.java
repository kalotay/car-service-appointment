package com.kalotay.appointment;

import javax.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    return appointmentRepository.fetch(id).orElseThrow(NotFoundException::new);
  }
}
