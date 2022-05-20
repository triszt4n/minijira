package hu.triszt4n.minijira.service;

import hu.triszt4n.minijira.entity.ProjectEntity;
import hu.triszt4n.minijira.entity.TaskEntity;
import hu.triszt4n.minijira.entity.UserEntity;
import hu.triszt4n.minijira.input.CreateTaskInput;
import hu.triszt4n.minijira.input.UpdateTaskInput;
import hu.triszt4n.minijira.repository.ProjectRepository;
import hu.triszt4n.minijira.repository.TaskRepository;
import hu.triszt4n.minijira.repository.UserRepository;
import hu.triszt4n.minijira.util.StatusEnum;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public List<TaskEntity> getAllByProject(ProjectEntity projectEntity) {
        return this.taskRepository.findByProject(projectEntity);
    }

    public List<TaskEntity> getTodosByProject(ProjectEntity projectEntity) {
        return this.taskRepository.findByProjectAndStatus(projectEntity, StatusEnum.TODO);
    }

    public List<TaskEntity> getAllAssignedOfUser(UserEntity userEntity) {
        return this.taskRepository.findByAssignedUsersIn(List.of(userEntity));
    }

    public TaskEntity getById(Long id) {
        return this.taskRepository.findById(id).orElse(null);
    }

    public void create(CreateTaskInput createTaskInput, Long projectId, UserEntity currentUser) throws IllegalArgumentException {
        final var projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project does not exist"));

        taskRepository.save(
                new TaskEntity()
                        .setProject(projectEntity)
                        .setHoursNeeded(createTaskInput.getHoursNeeded())
                        .setDescription(createTaskInput.getDescription())
                        .setStatus(StatusEnum.valueOf(createTaskInput.getStatusValue()))
                        .setTitle(createTaskInput.getTitle())
        );
    }

    public void update(Long id, UpdateTaskInput updateTaskInput) throws IllegalArgumentException {
        final var taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task does not exist"));

        taskEntity.setDescription(updateTaskInput.getDescription());
        taskEntity.setTitle(updateTaskInput.getTitle());
        taskEntity.setStatus(StatusEnum.valueOf(updateTaskInput.getStatusValue()));
        taskEntity.setHoursNeeded(updateTaskInput.getHoursNeeded());

        this.taskRepository.save(taskEntity);
    }

    public void addAssignee(Long id, Long assigneeId) {
        final var taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task does not exist"));

        final var userEntity = userRepository.findById(assigneeId)
                .orElseThrow(() -> new IllegalArgumentException("User to assign does not exist"));

        taskEntity.getAssignedUsers().add(userEntity);
        this.taskRepository.save(taskEntity);
    }

    public void removeAssignee(Long id, Long assigneeId) {
        final var taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task does not exist"));

        final var userEntity = userRepository.findById(assigneeId)
                .orElseThrow(() -> new IllegalArgumentException("User to unassign does not exist"));

        taskEntity.getAssignedUsers().remove(userEntity);
        this.taskRepository.save(taskEntity);
    }

    public void delete(Long id) {
        final var taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task does not exist"));

        taskRepository.delete(taskEntity);
    }
}
