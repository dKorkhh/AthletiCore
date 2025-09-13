package com.example.athleticore.controller.appointment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/session")
public class SessionController {

    //permission - manager(create new repeat session and connects trainer)
    @PostMapping("/")
    public void createSession(){
        //send notification to trainer to accept(optional)
    }


    //permission - manager(create new repeat session and connects trainer)
    @PostMapping("/update")
    public void updateSession(){

    }


    //permission - manager(create new repeat session and connects trainer)
    @DeleteMapping("/")
    public void deleteSession(){

    }

    @GetMapping("/")
    public void showAllSession(){

    }

    @GetMapping("/{idUser}")
    public void showSessionByUser(@PathVariable Long idUser){
        //return session.getVyId();
    }
}
