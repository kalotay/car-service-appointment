package com.kalotay.appointment;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class ControllersTest {

  private static final String APPOINTMENT = "{\"appointmentTime\": \"2020-03-04T18:15:15.454404\", \"price\": 52.30, \"details\": \"oil change\"}";
  @Autowired
  MockMvc mvc;

  @Test
  void appointmentCanBePosted() throws Exception {
    mvc.perform(post("/appointment")
        .content(APPOINTMENT)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.appointmentTime").value("2020-03-04T18:15:15.454404"))
        .andExpect(jsonPath("$.price").value(52.3))
        .andExpect(jsonPath("$.details").value("oil change"))
        .andExpect(jsonPath("$.id").exists());
  }
}
