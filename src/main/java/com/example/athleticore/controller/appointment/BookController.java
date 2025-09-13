package com.example.athleticore.controller.appointment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book")
public class BookController {

    //role - user
    @PostMapping("/")
    public void createBook(){
        //make booking and connecting to existing session
    }

    @DeleteMapping("/deleteBook")
    public void cancelBooking(){

    }

    //made by manager
    @PostMapping
    public void confirmBook(){

    }
}
