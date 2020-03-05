package com.kalotay.appointment;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;

class Helpers {
  @NotNull
  static LocalDateTime getAppointmentTime() {
    return LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
  }

  static Matcher<List<Appointment>> verifySortedByPrice() {
    return new TypeSafeMatcher<>() {
      @Override
      protected boolean matchesSafely(List<Appointment> item) {
        int bound = item.size() - 1;
        for (int i = 0; i < bound; i += 1) {
          if (item.get(i).getPrice() > item.get(i+1).getPrice()) {
            return false;
          }
        }
        return true;
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("appointments not sorted by price");
      }
    };
  }

  static Matcher<List<Appointment>> verifyBetweenDateRange(LocalDateTime from, LocalDateTime to) {
    return new TypeSafeMatcher<>() {
      @Override
      protected boolean matchesSafely(List<Appointment> item) {
        return item.stream()
            .map(Appointment::getAppointmentTime)
            .allMatch(t -> t.isAfter(from) && t.isBefore(to));
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("not all appointments in expected date range");
      }
    };
  }
}
