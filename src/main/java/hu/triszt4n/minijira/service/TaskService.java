package hu.triszt4n.minijira.service;

import hu.triszt4n.minijira.entity.ProjectEntity;
import hu.triszt4n.minijira.entity.TaskEntity;
import hu.triszt4n.minijira.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskEntity> getAllByProject(ProjectEntity projectEntity) {
        return this.taskRepository.findByProject(projectEntity);
    }

    public TaskEntity getById(Long id) {
        return this.taskRepository.findById(id).orElse(null);
    }
}
