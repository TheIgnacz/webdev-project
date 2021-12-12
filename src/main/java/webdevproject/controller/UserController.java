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
import webdevproject.database.model.UserEntity;
import webdevproject.database.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    List<UserEntity> findAll() {return userRepository.findAll();}

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    @GetMapping("/users/{name}")
    public String findUserByName(@PathVariable String name) {
        var user = userRepository.findUsersEntityByName(name);
        if (user.isEmpty()) {
            return "User not found";
        }
        return user.stream()
                .map(UserEntity::toString)
                .collect(Collectors.joining("\n"));
    }

    @PutMapping("users/{name}")
    Optional<UserEntity> update(@PathVariable String name, @RequestBody UserEntity userEntity) {
        return userRepository.findUsersEntityByName(name)
                .map(x -> {
                    x.setName(userEntity.getName());
                    x.setPw(userEntity.getPw());
                    x.setPrivileged(userEntity.getPrivileged());
                    return userRepository.save(x);
                });
    }

    @DeleteMapping("/users/{name}")
    public void deleteUser(@PathVariable String name) {
        userRepository.findUsersEntityByName(name).ifPresent(userRepository::delete);
    }
}
