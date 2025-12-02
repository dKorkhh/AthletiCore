package com.example.athleticore.controller.appointment;

import com.example.athleticore.dto.bookings.BookingResponseDTO;
import com.example.athleticore.entity.Booking;
import com.example.athleticore.entity.Session;
import com.example.athleticore.exception.limit.AlreadyBookedException;
import com.example.athleticore.exception.limit.MaxParticipantsReachedException;
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
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public String showPageToBookSession(@PathVariable Long sessionId, Model model){
        Session session = sessionService.getSessionById(sessionId);
        model.addAttribute("trainingSession", session);

        return "booking/bookingPage";
    }

    @PostMapping("/{sessionId}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public String createBooking(@PathVariable Long sessionId, Model model){
        try {
            BookingResponseDTO booking = bookingService.createBooking(sessionId);
            model.addAttribute("booking", booking);
            return "booking/booking-success";
        } catch (AlreadyBookedException | MaxParticipantsReachedException ex) {

            Session session = sessionService.getSessionById(sessionId);

            model.addAttribute("trainingSession", session);
            model.addAttribute("errorMessage", ex.getMessage());

            return "booking/bookingPage";
        }
    }

    @PostMapping("/cancel")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public String cancelBooking(@RequestParam Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return "redirect:/api/account";
    }
}
