package webdevproject.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webdevproject.database.model.MovieEntity;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieEntity, String> {

    List<MovieEntity> findAll();

    Optional<MovieEntity> findMoviesEntityByName(String name);
}
