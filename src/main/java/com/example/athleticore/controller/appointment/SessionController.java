package com.example.athleticore.controller.appointment;

import com.example.athleticore.dto.PageResponse;
import com.example.athleticore.dto.sessions.SessionDto;
import com.example.athleticore.entity.Booking;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.enums.Category;
import com.example.athleticore.enums.Difficulty;
import com.example.athleticore.enums.Role;
import com.example.athleticore.enums.SessionType;
import com.example.athleticore.exception.limit.SessionTimeOutOfRangeException;
import com.example.athleticore.service.impl.session.BookingServiceImpl;
import com.example.athleticore.service.impl.session.SessionServiceImpl;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import com.example.athleticore.utils.validation.LimitCallMethod;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final SessionServiceImpl sessionService;
    private final UserServiceImpl userService;
    private final BookingServiceImpl bookingService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @LimitCallMethod
    public String getSession(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(defaultValue = "date,asc") String sort) {

        User currentUser = userService.getCurrentUser();
        Role role = currentUser.getRole();

        PageResponse<Session> response;

        if (role == Role.ADMIN) {
            response = sessionService.getSessionsPage(page, size, sort);
            model.addAttribute("trainingSessions", response.getContent());
            model.addAttribute("page", response);
            return "session/admin-session";

        } else if (role == Role.TRAINER) {
            response = sessionService.getSessionsByTrainerPage(currentUser, page, size, sort);
            model.addAttribute("trainingSessions", response.getContent());
            model.addAttribute("page", response);
            return "session/trainer-session";

        } else {
            response = sessionService.getSessionsPage(page, size, sort);
            model.addAttribute("trainingSessions", response.getContent());
            model.addAttribute("page", response);
            return "session/CatalogSession";
        }
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("hasRole('TRAINER')")
    public String showDetailsOfSession(@PathVariable Long id, Model model) {
        Session session = sessionService.getSessionById(id);
        List<Booking> bookings = bookingService.findBookingsBySessionId(session.getId());

        model.addAttribute("trainingSession", session);
        model.addAttribute("bookings", bookings);
        return "session/session-details";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String showCreateSessionPage(Model model) {
        model.addAttribute("sessionDto", SessionDto.builder().build());

        model.addAttribute("sessionTypes", SessionType.values());
        model.addAttribute("categories", Category.values());
        model.addAttribute("difficulties", Difficulty.values());

        model.addAttribute("trainers", userService.findAllTrainers());

        return "session/add-session";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String add(@ModelAttribute SessionDto sessionDto, Model model) {
        try {
            sessionService.createSession(sessionDto);
            return "redirect:/api/sessions";
        }
        catch (SessionTimeOutOfRangeException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("session", sessionDto);

            return "session/add-session";
        }
    }

    @GetMapping("/edit/{id}")
    public String editSessionForm(@PathVariable Long id, Model model) {
        SessionDto dto = sessionService.getSessionDtoById(id);
        dto.setFormattedDate(dto.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));

        model.addAttribute("sessionDto", dto);
        model.addAttribute("sessionId", id);

        model.addAttribute("sessionTypes", SessionType.values());
        model.addAttribute("categories", Category.values());
        model.addAttribute("difficulties", Difficulty.values());
        model.addAttribute("trainers", userService.findAllTrainers());

        return "session/edit-session";
    }

    @PatchMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateSession(@PathVariable Long id,
                                @Valid @ModelAttribute("sessionDto") SessionDto sessionDto,
                                BindingResult result,
                                Model model){
        if (result.hasErrors()) {
            sessionDto.setFormattedDate(sessionDto.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
            model.addAttribute("sessionDto", sessionDto);
            model.addAttribute("sessionTypes", SessionType.values());
            model.addAttribute("categories", Category.values());
            model.addAttribute("difficulties", Difficulty.values());
            model.addAttribute("trainers", userService.findAllTrainers());
            model.addAttribute("sessionId", id);

            return "session/edit-session";
        }

        try {
            sessionService.updateSession(id, sessionDto);
            return "redirect:/api/sessions";
        }
        catch (SessionTimeOutOfRangeException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("session", sessionDto);
            return "session/edit-session";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteSession(@PathVariable Long id) {
        System.out.println("delete" + " " + id);
        sessionService.deleteSessionById(id);

        return "redirect:/api/sessions";
    }
}
