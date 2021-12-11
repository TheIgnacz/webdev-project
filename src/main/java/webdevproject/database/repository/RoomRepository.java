package webdevproject.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webdevproject.database.model.RoomEntity;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity, String> {

    List<RoomEntity> findAll();

    Optional<RoomEntity> findRoomsEntityByName(String name);
}
