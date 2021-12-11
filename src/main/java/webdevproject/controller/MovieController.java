package webdevproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import webdevproject.database.model.MovieEntity;
import webdevproject.database.model.UserEntity;
import webdevproject.database.repository.MovieRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/movies")
    public String listMovies() {
        var movies = movieRepository.findAll();
        if(movies.isEmpty()) {
            return "There are no movies at the moment";
        }
        return movies.stream()
                .map(MovieEntity::toString)
                .collect(Collectors.joining("\n"));
    }

    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMovie(@RequestBody MovieEntity movieEntity) {movieRepository.save(movieEntity);}

    @GetMapping("/movies/{name}")
    public String findMovieByName(@PathVariable String name) {
        var movie = movieRepository.findMoviesEntityByName(name);
        if(movie.isEmpty()) {
            return "Movie not found";
        }
        return movie.stream()
                .map(MovieEntity::toString)
                .collect(Collectors.joining("\n"));
    }

    @PutMapping("movies/{name}")
    Optional<MovieEntity> update(@PathVariable String name, @RequestBody MovieEntity movieEntity) {
        return movieRepository.findMoviesEntityByName(name)
                .map(x -> {
                    x.setName(movieEntity.getName());
                    x.setGenre(movieEntity.getGenre());
                    x.setPlaytime(movieEntity.getPlaytime());
                    return movieRepository.save(x);
                });
    }

    @DeleteMapping("/movies/{name}")
    public void deleteMovie(@PathVariable String name) {
        movieRepository.findMoviesEntityByName(name).ifPresent(movieRepository::delete);
    }
}
