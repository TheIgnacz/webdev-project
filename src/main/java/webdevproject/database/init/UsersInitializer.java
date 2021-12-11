package webdevproject.database.init;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import webdevproject.database.model.UserEntity;
import webdevproject.database.repository.UserRepository;

@Component
public class UsersInitializer {

    private final UserEntity adminUser;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UsersInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminUser = new UserEntity("admin", passwordEncoder.encode("admin"), true);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void init(ApplicationStartedEvent event) {
        var admin = userRepository.findUsersEntityByName("admin");
        if (admin.isEmpty()) {
            userRepository.save(adminUser);
        } else if (!admin.get().isPrivileged()) {
            admin.get().setPrivileged(true);
            userRepository.save(admin.get());
        }
    }
}
