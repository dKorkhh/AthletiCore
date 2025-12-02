package com.example.athleticore.controller.account;

import com.example.athleticore.entity.Booking;
import com.example.athleticore.service.impl.session.BookingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final BookingServiceImpl bookingService;

    @GetMapping("")
    public String getAccountPage(Model model) {
        List<Booking> bookings = bookingService.findBookingsByCurrentUser();
        model.addAttribute("bookings", bookings);

        return "account/accountPage";
    }

    @PostMapping("/cancel")
    public String cancelBooking(@RequestParam Long bookingId) {

        /*Booking booking = bookingService.findBookingById(bookingId);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingService.save(booking);*/

        return "redirect:/account";
    }
}
