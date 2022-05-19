package hu.triszt4n.minijira.controller;

import hu.triszt4n.minijira.dto.MessageDto;
import hu.triszt4n.minijira.input.CreateCommentInput;
import hu.triszt4n.minijira.input.CreateTaskInput;
import hu.triszt4n.minijira.input.UpdateTaskInput;
import hu.triszt4n.minijira.service.CommentService;
import hu.triszt4n.minijira.service.TaskService;
import hu.triszt4n.minijira.service.UserService;
import hu.triszt4n.minijira.util.MyUserPrincipal;
import hu.triszt4n.minijira.util.StatusEnum;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final CommentService commentService;
    private final UserService userService;

    public TaskController(TaskService taskService, CommentService commentService, UserService userService) {
        this.taskService = taskService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String taskPage(Model model,
                           @PathVariable Long id) {
        final var task = taskService.getById(id);
        if (task == null) {
            model.addAttribute("message", new MessageDto()
                    .setMessage("No task found under entered id")
                    .setType("danger"));
            return "/projects";
        }

        final var comments = commentService.getAllByTask(task);
        var assignableUsers = userService.getAll();
        assignableUsers.removeAll(task.getAssignedUsers());

        model.addAttribute("task", task);
        model.addAttribute("comments", comments);
        model.addAttribute("users", assignableUsers);
        model.addAttribute("createCommentInput", new CreateCommentInput());
        return "task";
    }

    @GetMapping("/new/{projectId}")
    public String newTaskPage(Model model,
                              @PathVariable Long projectId) {
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
    public String editTaskPage(Model model,
                               @PathVariable Long id) {
        final var task = taskService.getById(id);

        model.addAttribute("updateTaskInput", new UpdateTaskInput(task));
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

    @PostMapping("/{id}/assign")
    public String addAssignee(@RequestParam Map<String, String> requestParam,
                              @PathVariable Long id) {
        taskService.addAssignee(id, Long.parseLong(requestParam.get("assigneeId")));
        return "redirect:/tasks/".concat(String.valueOf(id));
    }

    @PostMapping("/{id}/unassign/{assigneeId}")
    public void removeAssignee(@PathVariable Long id,
                               @PathVariable Long assigneeId) {
        taskService.removeAssignee(id, assigneeId);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.delete(id);
    }
}
