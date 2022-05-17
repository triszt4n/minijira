package hu.triszt4n.minijira.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    private String loginPage() {
        return "index";
    }
}
