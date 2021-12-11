package webdevproject.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webdevproject.database.model.RoomEntity;
import webdevproject.database.model.ScreeningEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ScreeningRepository extends JpaRepository<ScreeningEntity, Integer> {

    List<ScreeningEntity> findAll();

    List<ScreeningEntity> findScreeningEntityByRoom(RoomEntity roomEntity);

    Optional<ScreeningEntity> findScreeningEntityByMovie_NameAndRoom_NameAndDate(
            String movieName, String roomName, Date date);
}
