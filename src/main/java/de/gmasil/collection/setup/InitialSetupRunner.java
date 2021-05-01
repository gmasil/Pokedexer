package de.gmasil.collection.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import de.gmasil.collection.security.User;
import de.gmasil.collection.security.UserRepository;
import de.gmasil.collection.security.UserService;

@Component
public class InitialSetupRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Value("${initial.user.name:#{null}}")
    private String username;

    @Value("${initial.user.password:#{null}}")
    private String password;

    @EventListener(ApplicationReadyEvent.class)
    public void setupInitialUser() {
        if (userRepository.count() == 0 && username != null && password != null) {
            User user = new User();
            user.setName(username);
            user.setPassword(password);
            userService.encodePassword(user);
            userService.save(user);
        }
    }
}
