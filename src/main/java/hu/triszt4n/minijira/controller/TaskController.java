package hu.triszt4n.minijira.controller;

import hu.triszt4n.minijira.dto.MessageDto;
import hu.triszt4n.minijira.input.CreateTaskInput;
import hu.triszt4n.minijira.input.UpdateTaskInput;
import hu.triszt4n.minijira.service.CommentService;
import hu.triszt4n.minijira.service.TaskService;
import hu.triszt4n.minijira.util.MyUserPrincipal;
import hu.triszt4n.minijira.util.StatusEnum;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final CommentService commentService;

    public TaskController(TaskService taskService, CommentService commentService) {
        this.taskService = taskService;
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public String taskPage(Model model, @PathVariable Long id) {
        final var task = taskService.getById(id);
        if (task == null) {
            model.addAttribute("message", new MessageDto()
                    .setMessage("No task found under entered id")
                    .setType("danger"));
            return "/projects";
        }

        final var comments = commentService.getAllByTask(task);

        model.addAttribute("task", task);
        model.addAttribute("comments", comments);
        return "task";
    }

    @GetMapping("/new/{projectId}")
    public String newTaskPage(Model model, @PathVariable Long projectId) {
        model.addAttribute("createTaskInput", new CreateTaskInput());
        model.addAttribute("statuses", StatusEnum.values());
        return "formPages/newTask";
    }

    @PostMapping("/new/{projectId}")
    public String createTask(@Valid @ModelAttribute("createTaskInput") CreateTaskInput createTaskInput,
                                BindingResult bindingResult,
                                Authentication authentication,
                                @PathVariable Long projectId) {
        if (bindingResult.hasErrors()) {
            return "formPages/newTask";
        }

        final var currentUser = (MyUserPrincipal) authentication.getPrincipal();
        taskService.create(createTaskInput, projectId, currentUser.getUserEntity());

        return "redirect:/projects/".concat(String.valueOf(projectId));
    }

    @GetMapping("/{id}/edit")
    public String editTaskPage(Model model, @PathVariable Long id) {
        final var task = taskService.getById(id);

        model.addAttribute("createTaskInput", new UpdateTaskInput(task));
        model.addAttribute("statuses", StatusEnum.values());
        return "formPages/editTask";
    }

    @PostMapping("/{id}/edit")
    public String updateTask(@Valid @ModelAttribute("updateTaskInput") UpdateTaskInput updateTaskInput,
                             BindingResult bindingResult,
                             @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return "formPages/editTask";
        }

        taskService.update(id, updateTaskInput);
        return "redirect:/tasks/".concat(String.valueOf(id));
    }
}
