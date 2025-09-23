package com.example.athleticore.controller.appointment;

import com.example.athleticore.entity.Booking;
import com.example.athleticore.dto.bookings.BookingDto;
import com.example.athleticore.dto.bookings.UpdateBookingFields;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/book")
@Validated
public class BookingController {
    @GetMapping("/")
    public List<Booking> getAllBookings(){
        return Collections.emptyList();
    }

    @GetMapping("/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable Long userId){
        return Collections.emptyList();
    }

    //role - user
    @PostMapping("/")
    public void createBooking(@Valid @RequestBody BookingDto bookingDto){
        //make booking and connecting to existing session
    }

    @PatchMapping("/{id}")
    public void updateBooking(@PathVariable Long id, @RequestBody UpdateBookingFields updateBookingFields){

    }

    @DeleteMapping("/deleteBook")
    public void cancelBooking(){

    }
}
