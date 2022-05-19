package hu.triszt4n.minijira.controller;

import hu.triszt4n.minijira.dto.MessageDto;
import hu.triszt4n.minijira.input.LoginUserInput;
import hu.triszt4n.minijira.util.MyUserPrincipal;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    @GetMapping("/")
    public String loginPage(@RequestParam Map<String, String> queryParams, Model model) {
        if (queryParams.get("success") != null) {
            model.addAttribute("message", new MessageDto()
                    .setMessage("Successfully registered!")
                    .setType("success"));
        }

        if (queryParams.get("error") != null) {
            model.addAttribute("message", new MessageDto()
                    .setMessage("Invalid credentials")
                    .setType("danger"));
        }

        model.addAttribute("loginUserInput", new LoginUserInput());
        return "index";
    }

    @GetMapping("/profile")
    public String loginPage(Authentication authentication) {
        final var principal = (MyUserPrincipal) authentication.getPrincipal();
        final var currentUser = principal.getUserEntity();
        return "redirect:/users/".concat(String.valueOf(currentUser.getId()));
    }
}
