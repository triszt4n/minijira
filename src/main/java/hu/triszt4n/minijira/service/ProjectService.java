package hu.triszt4n.minijira.service;

import hu.triszt4n.minijira.entity.ProjectEntity;
import hu.triszt4n.minijira.entity.UserEntity;
import hu.triszt4n.minijira.input.CreateProjectInput;
import hu.triszt4n.minijira.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectEntity> getAll() {
        return this.projectRepository.findAll();
    }

    public void create(CreateProjectInput createProjectInput, UserEntity currentUser) {
        if (currentUser == null) {
            throw new IllegalArgumentException("User not logged in");
        }

        this.projectRepository.save(
                new ProjectEntity()
                        .setDescription(createProjectInput.getDescription())
                        .setTitle(createProjectInput.getTitle())
                        .setManager(currentUser)
        );
    }

    public ProjectEntity getById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }
}
