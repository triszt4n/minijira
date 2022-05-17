package hu.triszt4n.minijira.controller;

import hu.triszt4n.minijira.dto.CreateUserDto;
import hu.triszt4n.minijira.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller()
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    private String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    private String register(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return "redirect:/projects";
    }
}