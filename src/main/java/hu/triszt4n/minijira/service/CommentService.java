package hu.triszt4n.minijira.service;

import hu.triszt4n.minijira.entity.CommentEntity;
import hu.triszt4n.minijira.entity.TaskEntity;
import hu.triszt4n.minijira.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<CommentEntity> getAllByTask(TaskEntity taskEntity) {
        return commentRepository.findByTask(taskEntity);
    }
}
