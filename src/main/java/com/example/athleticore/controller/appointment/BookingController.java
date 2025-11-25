package com.example.athleticore.controller.appointment;

import com.example.athleticore.dto.bookings.BookingResponseDTO;
import com.example.athleticore.entity.Booking;
import com.example.athleticore.dto.bookings.BookingDto;
import com.example.athleticore.dto.bookings.UpdateBookingFields;
import com.example.athleticore.entity.Session;
import com.example.athleticore.service.impl.session.BookingServiceImpl;
import com.example.athleticore.service.impl.session.SessionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/api/booking")
@Validated
@RequiredArgsConstructor
public class BookingController {
    private final SessionServiceImpl sessionService;
    private final BookingServiceImpl bookingService;

    @GetMapping("/")
    public List<Booking> getAllBookings(){
        return Collections.emptyList();
    }

    @GetMapping("/{sessionId}")
    //@PreAuthorize("hasRole('ROLE_CLIENT')")
    public String showBookingPage(@PathVariable Long sessionId, Model model){
        Session session = sessionService.getSessionById(sessionId);
        model.addAttribute("trainingSession", session);

        return "booking/bookingPage";
    }

    //role - userF
    @PostMapping("/{sessionId}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public String createBooking(@PathVariable Long sessionId, Model model){
        BookingResponseDTO booking = bookingService.createBooking(sessionId);
        model.addAttribute("booking", booking);
        System.out.println(sessionId);

        return "booking/booking-success";
    }


    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    public void updateBooking(@PathVariable Long id, @RequestBody UpdateBookingFields updateBookingFields){

    }

    @DeleteMapping("/deleteBook")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    public void cancelBooking(){

    }


}
