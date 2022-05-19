package hu.triszt4n.minijira.repository;

import hu.triszt4n.minijira.entity.ProjectEntity;
import hu.triszt4n.minijira.entity.TaskEntity;
import hu.triszt4n.minijira.entity.UserEntity;
import hu.triszt4n.minijira.util.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByProject(ProjectEntity project);

    List<TaskEntity> findByProjectAndStatus(ProjectEntity project, StatusEnum status);

    List<TaskEntity> findByAssignedUsersIn(Collection<UserEntity> assignedUsers);
}
