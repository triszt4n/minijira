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

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("createUserInput", new CreateUserInput());
        return "formPages/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("createUserInput") CreateUserInput createUserInput,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "formPages/register";
        }

        try {
            userService.createUser(createUserInput);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("username", "error.createUserInput", e.getMessage());
            return "formPages/register";
        }

        return "redirect:/?success";
    }
}