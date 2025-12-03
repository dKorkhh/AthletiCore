package com.example.athleticore.controller.account;

import com.example.athleticore.entity.Booking;
import com.example.athleticore.service.impl.session.BookingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final BookingServiceImpl bookingService;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public String getAccountPage(Model model) {
        List<Booking> bookings = bookingService.findBookingsByCurrentUser();
        model.addAttribute("bookings", bookings);

        return "account/accountPage";
    }
}
