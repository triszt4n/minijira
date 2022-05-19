package hu.triszt4n.minijira.controller;

import hu.triszt4n.minijira.service.TaskService;
import hu.triszt4n.minijira.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final TaskService taskService;

    public UserController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public String userPage(@PathVariable Long id,
                           Model model) {
        final var user = userService.getById(id);
        final var assignedTasks = taskService.getAllAssignedOfUser(user);

        model.addAttribute("user", user);
        model.addAttribute("tasks", assignedTasks);
        return "user";
    }
}
