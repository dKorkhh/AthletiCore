package com.example.athleticore.controller.users;

import com.example.athleticore.dto.PageResponse;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final UserServiceImpl userService;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public String clientsPage(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id,asc") String sort) {

        PageResponse<User> response = userService.getClientsPage(page, size, sort);
        model.addAttribute("users", response.getContent());
        model.addAttribute("page", response);
        model.addAttribute("isTrainerPage", false);
        return "user/user-list";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteClientById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
