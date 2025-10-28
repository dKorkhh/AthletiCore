package com.example.athleticore.controller.users;

import com.example.athleticore.dto.patch.PatchDto;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor()
public class ClientController {
    private final UserServiceImpl userService;

    @GetMapping("/")
    public List<User> getAllClients(){
        return userService.getAllUser();
    }

    @DeleteMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteClientById(@RequestParam Long id){
        userService.deleteUserById(id);
    }

    @PatchMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    public User updateClient(@RequestBody PatchDto patchDto){
        return userService.updateUser(patchDto);
    }
}
