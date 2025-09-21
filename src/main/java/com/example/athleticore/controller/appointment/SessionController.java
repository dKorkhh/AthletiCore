package com.example.athleticore.controller.appointment;

import com.example.athleticore.dto.sessions.SessionDto;
import com.example.athleticore.dto.sessions.UpdateSessionFields;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {
    @GetMapping("/{idUser}")
    public void getSessionByUser(@PathVariable Long idUser){
        //return session.getVyId();
    }

    //permission - manager(create new repeat session and connects trainer)
    @PostMapping("/")
    public void createSession(@Valid @RequestBody SessionDto sessionDto){
        //send notification to trainer to accept(optional)
    }

    //permission - manager(create new repeat session and connects trainer)
    @PatchMapping("/{id}")
    public void updateSession(@PathVariable Long id, @RequestBody UpdateSessionFields updateSessionFields){

    }

    //permission - manager(create new repeat session and connects trainer)
    @DeleteMapping("/")
    public void deleteSession(){

    }
}
