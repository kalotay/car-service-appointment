package com.kalotay.appointment;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Data
@Value
@RequiredArgsConstructor
class Appointment {
  String id;
  LocalDateTime appointmentTime;
  double price;
  String details;
}
