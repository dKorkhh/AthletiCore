package com.example.athleticore.controller.appointment;

import com.example.athleticore.entity.Booking;
import com.example.athleticore.dto.bookings.BookingDto;
import com.example.athleticore.dto.bookings.UpdateBookingFields;
import com.example.athleticore.service.impl.session.SessionServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/api/book")
@Validated
@RequiredArgsConstructor
public class BookingController {
    private final SessionServiceImpl sessionService;

    @GetMapping("/")
    public List<Booking> getAllBookings(){
        return Collections.emptyList();
    }

    @GetMapping("/{sessionId}")
    //@PreAuthorize("hasRole('ROLE_CLIENT')")
    public String showBookingPage(@PathVariable Long sessionId, Model model){
        //make booking and connecting to existing session
        BookingDto bookingDto = BookingDto.builder()
                .date(sessionService.getSessionById(sessionId).getDate()).build();
        model.addAttribute("bookingDto", bookingDto);

        return "booking/bookingPage";
    }

    //role - user
    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public void createBooking(@Valid @RequestBody BookingDto bookingDto){
        //make booking and connecting to existing session
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
