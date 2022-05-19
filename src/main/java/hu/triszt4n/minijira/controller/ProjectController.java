package hu.triszt4n.minijira.controller;

import hu.triszt4n.minijira.dto.MessageDto;
import hu.triszt4n.minijira.entity.ProjectEntity;
import hu.triszt4n.minijira.entity.TaskEntity;
import hu.triszt4n.minijira.input.CreateProjectInput;
import hu.triszt4n.minijira.input.UpdateProjectInput;
import hu.triszt4n.minijira.service.ProjectService;
import hu.triszt4n.minijira.service.TaskService;
import hu.triszt4n.minijira.util.MyUserPrincipal;
import hu.triszt4n.minijira.util.StatusEnum;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final TaskService taskService;

    public ProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @GetMapping("")
    public String projectsPage(Model model) {
        final var allProjects = projectService
                .getAll()
                .stream()
                .collect(Collectors.partitioningBy(ProjectEntity::isClosed));

        model.addAttribute("openProjects", allProjects.get(false));
        model.addAttribute("closedProjects", allProjects.get(true));
        return "projects";
    }

    @GetMapping("/new")
    public String newProjectPage(Model model) {
        model.addAttribute("createProjectInput", new CreateProjectInput());
        return "formPages/newProject";
    }

    @PostMapping("/new")
    public String createProject(@Valid @ModelAttribute("createProjectInput") CreateProjectInput createProjectInput,
                                BindingResult bindingResult,
                                Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "formPages/newProject";
        }

        final var currentUser = (MyUserPrincipal) authentication.getPrincipal();
        projectService.create(createProjectInput, currentUser.getUserEntity());

        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String editProjectPage(Model model, @PathVariable Long id) {
        final var project = projectService.getById(id);

        model.addAttribute("updateProjectInput", new UpdateProjectInput(project));
        return "formPages/editProject";
    }

    @PostMapping("/{id}/edit")
    public String updateProject(@Valid @ModelAttribute("updateProjectInput") UpdateProjectInput updateProjectInput,
                                BindingResult bindingResult,
                                @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return "formPages/editProject";
        }

        projectService.update(id, updateProjectInput);
        return "redirect:/projects/".concat(String.valueOf(id));
    }

    @GetMapping("/{id}")
    public String projectByIdPage(@PathVariable Long id, Model model) {
        final var project = projectService.getById(id);
        if (project == null) {
            model.addAttribute("message", new MessageDto()
                    .setMessage("No project found under entered id")
                    .setType("danger"));
            return "projects";
        }

        model.addAttribute("project", project);
        model.addAttribute("statuses", StatusEnum.values());

        final var tasksMap = new HashMap<StatusEnum, List<TaskEntity>>();
        final var tasks = taskService.getAllByProject(project);
        for (var status : StatusEnum.values()) {
            final var filteredTasks = tasks.stream().filter(taskEntity -> taskEntity.getStatus() == status).collect(Collectors.toList());
            tasksMap.put(status, filteredTasks);
        }

        model.addAttribute("tasksMap", tasksMap);

        return "project";
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.delete(id);
    }
}
