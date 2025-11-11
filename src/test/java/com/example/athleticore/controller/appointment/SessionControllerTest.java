package com.example.athleticore.controller.appointment;

import com.example.athleticore.entity.Session;
import com.example.athleticore.service.impl.session.SessionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class SessionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionServiceImpl sessionService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnSessionByUser() throws Exception {
        mockMvc.perform(get("/api/sessions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldBeForbiddenForUserGetSessionByUser() throws Exception {
        mockMvc.perform(get("/api/sessions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void shouldReturnSessionForTrainerGetSession() throws Exception {
        mockMvc.perform(get("/api/sessions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnCatalogSessionView() throws Exception {
        List<Session> sessions = List.of(
                Session.builder().name("Session 1").build(),
                Session.builder().name("Session 2").build()
        );

        when(sessionService.getSessions()).thenReturn(sessions);

        mockMvc.perform(get("/api/sessions"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trainingSessions"))
                .andExpect(model().attribute("trainingSessions", sessions))
                .andExpect(view().name("session/CatalogSession"));
    }
}