package hu.triszt4n.minijira.controller;

import hu.triszt4n.minijira.input.CreateUserInput;
import hu.triszt4n.minijira.dto.MessageDto;
import hu.triszt4n.minijira.input.LoginUserInput;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @GetMapping("/")
    public String loginPage(@RequestParam @Nullable String success, Model model) {
        if (success != null) {
            model.addAttribute("message", new MessageDto()
                    .setMessage("Successfully registered!")
                    .setType("success"));
        }
        model.addAttribute("loginUserInput", new LoginUserInput());
        return "index";
    }
}
