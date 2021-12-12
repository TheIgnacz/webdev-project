package webdevproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import webdevproject.WebdevProjectApplication;
import webdevproject.database.model.MovieEntity;
import webdevproject.database.model.RoomEntity;
import webdevproject.database.model.ScreeningEntity;
import webdevproject.database.repository.MovieRepository;
import webdevproject.database.repository.RoomRepository;
import webdevproject.database.repository.ScreeningRepository;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController

public class ScreeningController {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping("/screenings")
    public String listScreenings() {
        var screenings = screeningRepository.findAll();
        if (screenings.isEmpty()) {
            return "There are no screenings at the moment";
        }
        return screenings.stream()
                .map(ScreeningEntity::toString)
                .collect(Collectors.joining("\n"));
    }

    @PostMapping("/screenings")
    @ResponseStatus(HttpStatus.CREATED)
    public String createScreening(@RequestParam String movieName,
                                  @RequestParam String roomName,
                                  @RequestParam String date) throws ParseException {
        Date parsed = WebdevProjectApplication.simpleDateFormat.parse(date);
        Optional<MovieEntity> optionalMovie = movieRepository.findMoviesEntityByName(movieName);
        Optional<RoomEntity> optionalRoom = roomRepository.findRoomsEntityByName(roomName);
        List<ScreeningEntity> inRoomList =
                screeningRepository.findScreeningEntityByRoom(optionalRoom.orElse(null));
        if (!inRoomList.isEmpty()) {
            Instant instant = parsed.toInstant();
            Optional<ScreeningError> error =
                    inRoomList.stream()
                            .map(screeningEntity -> {
                                var playtime = optionalMovie.map(MovieEntity::getPlaytime).orElse(0);
                                return ScreeningError.getScreeningError(screeningEntity, playtime, instant);
                            }).filter(Predicate.not(ScreeningError.NONE::equals)).findFirst();
            if (error.isPresent()) {
                switch (error.get()) {
                    case OVERLAPPING:
                        return "There is an overlapping screening";
                    case NO_BREAK:
                        return "This would start in the break"
                                + " period after another screening in this room";
                    default:
                        break;
                }
            }
        }
        screeningRepository.save(new ScreeningEntity(optionalMovie.orElse(null), optionalRoom.orElse(null), parsed));
        return null;
    }

    public enum ScreeningError {
        NONE, OVERLAPPING, NO_BREAK;

        public static ScreeningError getScreeningError(ScreeningEntity screeningEntity,
                                                       Integer playtime,
                                                       Instant instant) {
            var screeningDate = screeningEntity.getDate().toInstant();
            var screeningEnd = screeningDate.plus(screeningEntity.getMovie().getPlaytime(), ChronoUnit.MINUTES);
            var screeningWithBreak = screeningEnd.plus(10, ChronoUnit.MINUTES);
            if (instant.isAfter(screeningDate)) {
                if (instant.isBefore(screeningEnd)) {
                    return ScreeningError.OVERLAPPING;

                } else if (instant.isBefore(screeningWithBreak)) {
                    return ScreeningError.NO_BREAK;
                }
                return ScreeningError.NONE;
            } else if (instant.isBefore(screeningEnd)
                    && instant.plus(playtime, ChronoUnit.MINUTES).isAfter(screeningEnd)) {
                return ScreeningError.OVERLAPPING;
            } else if (instant.isBefore(screeningWithBreak)
                    && instant.plus(playtime, ChronoUnit.MINUTES).isAfter(screeningWithBreak)) {
                return ScreeningError.NO_BREAK;
            }
            return ScreeningError.NONE;
        }
    }

    @DeleteMapping("/screenings")
    public void deleteScreenings(@RequestParam String movieName,
                                 @RequestParam String roomName,
                                 @RequestParam String date) throws ParseException {
        screeningRepository.findScreeningEntityByMovie_NameAndRoom_NameAndDate(movieName, roomName, WebdevProjectApplication.simpleDateFormat.parse(date)).ifPresent(screeningRepository::delete);
    }
}
