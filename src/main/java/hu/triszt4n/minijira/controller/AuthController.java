package hu.triszt4n.minijira.controller;

import hu.triszt4n.minijira.input.CreateUserInput;
import hu.triszt4n.minijira.input.LoginUserInput;
import hu.triszt4n.minijira.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller()
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginUserInput") LoginUserInput loginUserInput,
                        BindingResult bindingResult) {
        final var user = userService.getUser(loginUserInput);
        if (user == null) {
            bindingResult.rejectValue("username", "error.loginCredentials", "Invalid credentials");
            return "index";
        }

        return "redirect:/projects";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("createUserInput", new CreateUserInput());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("createUserInput") CreateUserInput createUserInput,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.createUser(createUserInput);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("username", "error.createUserInput", e.getMessage());
            return "register";
        }

        return "redirect:/?success";
    }
}