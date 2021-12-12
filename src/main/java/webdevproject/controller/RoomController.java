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
import webdevproject.database.model.RoomEntity;
import webdevproject.database.repository.RoomRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/rooms")
    public String listRoom() {
        var rooms = roomRepository.findAll();
        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        }
        return rooms.stream()
                .map(RoomEntity::toString)
                .collect(Collectors.joining("\n"));
    }

    @PostMapping("/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRoom(@RequestBody RoomEntity roomEntity) {
        roomRepository.save(roomEntity);
    }

    @GetMapping("/rooms/{name}")
    public String findRoomByName(@PathVariable String name) {
        var room = roomRepository.findRoomsEntityByName(name);
        if (room.isEmpty()) {
            return "Room not found";
        }
        return room.stream()
                .map(RoomEntity::toString)
                .collect(Collectors.joining("\n"));
    }

    @PutMapping("rooms/{name}")
    Optional<RoomEntity> update(@PathVariable String name, @RequestBody RoomEntity roomEntity) {
        return roomRepository.findRoomsEntityByName(name)
                .map(x -> {
                    x.setName(roomEntity.getName());
                    x.setChairRows(roomEntity.getChairRows());
                    x.setChairColumn(roomEntity.getChairColumn());
                    return roomRepository.save(x);
                });
    }

    @DeleteMapping("/rooms/{name}")
    public void deleteRoom(@PathVariable String name) {
        roomRepository.findRoomsEntityByName(name).ifPresent(roomRepository::delete);
    }
}
