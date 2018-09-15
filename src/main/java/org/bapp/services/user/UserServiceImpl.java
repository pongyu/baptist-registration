package org.bapp.services.user;

import org.bapp.model.User;
import org.bapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Long id) {

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return user.get();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        user.setCredentialsExpired(true);
        userRepository.save(user);
        logger.info("Disabled user {}", user.getUsername());
    }

    @Override
    public User findByUsername(String username) {
        logger.info("Find user with username {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUsernameAndEmail(String username, String email) {
        logger.info("Find user with username {} and email {}", username, email);
        return userRepository.findByUsernameAndEmail(username, email);
    }

    @Override
    public List<User> findByEnabled(boolean enabled) {
        logger.info("Find all enabled users");
        return userRepository.findByEnabled(true);
    }

    @Override
    public User createUser(User u) {
        User user = new User();
        user.setUsername(u.getUsername());
        user.setAccountExpired(u.isAccountExpired());
        user.setAccountLocked(u.isAccountLocked());
        user.setCredentialsExpired(u.isCredentialsExpired());
        user.setEmail(u.getEmail());
        user.setEnabled(u.isEnabled());
        user.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
        user.setRoles(u.getRoles());
        userRepository.save(user);
        logger.info("Created Information for User: {}", user);
        return user;
    }
}
