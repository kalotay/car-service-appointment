package com.kalotay.appointment;

import java.time.format.DateTimeParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadQueryParametersException extends RuntimeException {

  public BadQueryParametersException(DateTimeParseException e) {
    super(e);
  }
}
