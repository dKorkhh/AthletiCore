package com.example.athleticore.controller.users;

import com.example.athleticore.dto.PageResponse;
import com.example.athleticore.dto.user.FullName;
import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerController {

    private final UserServiceImpl userService;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public String trainersPage(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id,asc") String sort) {
        PageResponse<User> response = userService.getTrainersPage(page, size, sort);
        model.addAttribute("users", response.getContent());
        model.addAttribute("page", response);
        model.addAttribute("isTrainerPage", true);
        return "user/user-list";
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String pageCreateTrainer(Model model) {
        if (!model.containsAttribute("userDto")) {
            UserDto userDto = UserDto.builder()
                    .fullName(FullName.builder().build())
                    .build();
            model.addAttribute("userDto", userDto);
            model.addAttribute("isTrainer", true);
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserDto userDto) {
        userService.addTrainer(userDto);
        return "redirect:/api/trainers";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTrainerById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
