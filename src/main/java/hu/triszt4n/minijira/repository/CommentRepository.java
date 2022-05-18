package hu.triszt4n.minijira.repository;

import hu.triszt4n.minijira.entity.CommentEntity;
import hu.triszt4n.minijira.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByTask(TaskEntity task);
}
