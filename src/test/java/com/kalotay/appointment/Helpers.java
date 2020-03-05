package com.kalotay.appointment;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.jetbrains.annotations.NotNull;

public class Helpers {
  @NotNull
  public static LocalDateTime getAppointmentTime() {
    return LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
  }

}
