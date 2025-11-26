package com.example.athleticore.controller.appointment;

import com.example.athleticore.dto.sessions.SessionDto;
import com.example.athleticore.dto.sessions.UpdateSessionFields;
import com.example.athleticore.entity.Session;
import com.example.athleticore.service.impl.session.SessionServiceImpl;
import com.example.athleticore.utils.validation.LimitCallMethod;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final SessionServiceImpl sessionService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @LimitCallMethod
    public String getSession(Model model) {
        model.addAttribute("trainingSessions", sessionService.getSessions());
        return "session/CatalogSession";
    }

    @GetMapping("/{idUser}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    @ResponseStatus(HttpStatus.OK)
    public void getSessionByUser(@PathVariable Long idUser){
        //return session.getVyId();
    }

    //permission - manager(create new repeat session and connects trainer)
    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Session createSession(@Valid @RequestBody SessionDto sessionDto){
        return sessionService.createSession(sessionDto);
    }

    //permission - manager(create new repeat session and connects trainer)
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    public void updateSession(@PathVariable Long id, @RequestBody UpdateSessionFields updateSessionFields){

    }

    //permission - manager(create new repeat session and connects trainer)
    @DeleteMapping("/")
    @PreAuthorize("hasRole('CLIENT')")
    public void deleteSession(){

    }
}
