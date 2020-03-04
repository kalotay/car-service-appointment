package com.kalotay.appointment;

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppointmentController {

  @PostMapping("/appointment")
  public Appointment create(@RequestBody Appointment appointment) {
    return new Appointment("0", appointment.getAppointmentTime(), appointment.getPrice(), appointment.getDetails());
  }

  @GetMapping("/appointment")
  public Appointment create() {
    return new Appointment("0", LocalDateTime.now(), 0, "");
  }
}
