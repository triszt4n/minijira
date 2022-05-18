package hu.triszt4n.minijira.controller;

import hu.triszt4n.minijira.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/")
    public String projectsPage() {
        return "projects";
    }

    @GetMapping("/{id}")
    public String projectByIdPage(@PathVariable String id) {
        return "project";
    }
}
