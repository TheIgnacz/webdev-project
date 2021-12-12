package webdevproject.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webdevproject.database.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findUsersEntityByName(String name);

    long deleteUserEntityByName(String name);
}
