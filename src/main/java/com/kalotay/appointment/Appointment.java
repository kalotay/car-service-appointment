package com.kalotay.appointment;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;

public final class Appointment {
  @Id
  private final Long id;

  private final LocalDateTime appointmentTime;

  private final double price;

  private final String details;

  Appointment(Long id, LocalDateTime appointmentTime, double price, String details) {
    this.id = id;
    this.appointmentTime = appointmentTime;
    this.price = price;
    this.details = details;
  }

  public static Appointment of(LocalDateTime appointmentTime, double price, String details) {
    return new Appointment(null, appointmentTime, price, details);
  }

  Appointment withId(Long id) {
    return new Appointment(id, appointmentTime, price, details);
  }

  public Long getId() {
    return this.id;
  }

  public LocalDateTime getAppointmentTime() {
    return this.appointmentTime;
  }

  public double getPrice() {
    return this.price;
  }

  public String getDetails() {
    return this.details;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Appointment)) {
      return false;
    }
    final Appointment other = (Appointment) o;
    final Object this$id = this.getId();
    final Object other$id = other.getId();
    if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
      return false;
    }
    final Object this$appointmentTime = this.getAppointmentTime();
    final Object other$appointmentTime = other.getAppointmentTime();
    if (this$appointmentTime == null ? other$appointmentTime != null
        : !this$appointmentTime.equals(other$appointmentTime)) {
      return false;
    }
    if (Double.compare(this.getPrice(), other.getPrice()) != 0) {
      return false;
    }
    final Object this$details = this.getDetails();
    final Object other$details = other.getDetails();
    if (this$details == null ? other$details != null : !this$details.equals(other$details)) {
      return false;
    }
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $id = this.getId();
    result = result * PRIME + ($id == null ? 43 : $id.hashCode());
    final Object $appointmentTime = this.getAppointmentTime();
    result = result * PRIME + ($appointmentTime == null ? 43 : $appointmentTime.hashCode());
    final long $price = Double.doubleToLongBits(this.getPrice());
    result = result * PRIME + (int) ($price >>> 32 ^ $price);
    final Object $details = this.getDetails();
    result = result * PRIME + ($details == null ? 43 : $details.hashCode());
    return result;
  }

  public String toString() {
    return "Appointment(id=" + this.getId() + ", appointmentTime=" + this.getAppointmentTime()
        + ", price=" + this.getPrice() + ", details=" + this.getDetails() + ")";
  }
}
