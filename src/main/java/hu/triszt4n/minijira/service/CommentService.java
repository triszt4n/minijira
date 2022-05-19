package hu.triszt4n.minijira.service;

import hu.triszt4n.minijira.entity.CommentEntity;
import hu.triszt4n.minijira.entity.TaskEntity;
import hu.triszt4n.minijira.entity.UserEntity;
import hu.triszt4n.minijira.input.CreateCommentInput;
import hu.triszt4n.minijira.input.UpdateCommentInput;
import hu.triszt4n.minijira.repository.CommentRepository;
import hu.triszt4n.minijira.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
    }

    public List<CommentEntity> getAllByTask(TaskEntity taskEntity) {
        return commentRepository.findByTask(taskEntity);
    }

    public CommentEntity getById(Long id) {
        return this.commentRepository.findById(id).orElse(null);
    }

    public void create(CreateCommentInput createCommentInput, Long taskId, UserEntity currentUser) throws IllegalArgumentException {
        final var taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task does not exist"));

        commentRepository.save(
                new CommentEntity()
                        .setTask(taskEntity)
                        .setPublisher(currentUser)
                        .setBody(createCommentInput.getBody())
        );
    }

    public void update(Long id, UpdateCommentInput updateCommentInput) throws IllegalArgumentException {
        final var commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment does not exist"));

        commentEntity.setBody(updateCommentInput.getBody());
        commentRepository.save(commentEntity);
    }

    public void delete(Long id) {
        final var commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment does not exist"));

        commentRepository.delete(commentEntity);
    }
}
