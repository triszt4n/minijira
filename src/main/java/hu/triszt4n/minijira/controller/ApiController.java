package hu.triszt4n.minijira.controller;

import hu.triszt4n.minijira.dto.CommentDto;
import hu.triszt4n.minijira.dto.TaskDto;
import hu.triszt4n.minijira.dto.UserDto;
import hu.triszt4n.minijira.service.CommentService;
import hu.triszt4n.minijira.service.ProjectService;
import hu.triszt4n.minijira.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final TaskService taskService;
    private final ProjectService projectService;
    private final CommentService commentService;

    public ApiController(TaskService taskService, ProjectService projectService, CommentService commentService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.commentService = commentService;
    }

    @GetMapping("/projects/{id}/todos")
    public ResponseEntity<List<TaskDto>> todos(@PathVariable Long id) {
        final var projectEntity = projectService.getById(id);
        if (projectEntity == null) {
            return ResponseEntity.notFound().build();
        }

        final var tasks = taskService.getTodosByProject(projectEntity);
        return ResponseEntity.ok(
                tasks.stream()
                        .map(TaskDto::convert)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/tasks/{id}/assignees")
    public ResponseEntity<List<UserDto>> assignees(@PathVariable Long id) {
        final var taskEntity = taskService.getById(id);
        if (taskEntity == null) {
            return ResponseEntity.notFound().build();
        }

        final var users = taskEntity.getAssignedUsers();
        return ResponseEntity.ok(
                users.stream()
                        .map(UserDto::convert)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/tasks/{id}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long id) {
        final var taskEntity = taskService.getById(id);
        if (taskEntity == null) {
            return ResponseEntity.notFound().build();
        }

        final var comments = commentService.getAllByTask(taskEntity);
        return ResponseEntity.ok(
                comments.stream()
                        .map(CommentDto::convert)
                        .collect(Collectors.toList())
        );
    }
}
