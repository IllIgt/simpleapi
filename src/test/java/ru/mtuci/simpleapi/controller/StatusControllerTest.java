package ru.mtuci.simpleapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.net.InetAddress;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = StatusController.class)
@ActiveProfiles("test")
class StatusControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getStatus() throws Exception {

            String host = InetAddress.getLocalHost().getHostAddress();

            this.mockMvc.perform(get("/api/v1/status")).andExpect(status().isOk())
                    .andExpect(jsonPath("$.hostName", is(host)));
    }
}