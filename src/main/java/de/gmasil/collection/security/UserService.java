package de.gmasil.collection.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> optional = userRepository.findByName(username);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new UsernameNotFoundException(String.format("The username '%s' does not exist", username));
    }

    public void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean hasUsers() {
        return userRepository.count() > 0;
    }
}
