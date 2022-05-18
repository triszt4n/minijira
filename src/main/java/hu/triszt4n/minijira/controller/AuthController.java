package hu.triszt4n.minijira.controller;

import hu.triszt4n.minijira.dto.CreateUserDto;
import hu.triszt4n.minijira.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "index";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return "redirect:/projects";
    }
}