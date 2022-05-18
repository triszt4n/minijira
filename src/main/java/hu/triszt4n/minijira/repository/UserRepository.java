package hu.triszt4n.minijira.repository;

import hu.triszt4n.minijira.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsernameIgnoreCase(String username);
    Optional<UserEntity> findByUsernameIgnoreCase(String username);


}
