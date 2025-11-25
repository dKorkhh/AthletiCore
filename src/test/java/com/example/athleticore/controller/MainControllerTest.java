package com.example.athleticore.controller;

import com.example.athleticore.security.SecurityConfig;
import com.example.athleticore.service.impl.calendar.HolidayService;
import com.example.athleticore.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({SecurityConfig.class, JwtService.class})
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HolidayService holidayService;

    @Test
    void getHomePage_shouldReturnIndex() throws Exception {
        mockMvc.perform(get("/api/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("sessions"))
                .andExpect(model().attributeExists("holidays"));
    }
}
