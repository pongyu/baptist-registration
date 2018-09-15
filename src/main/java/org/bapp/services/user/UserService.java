package org.bapp.services.user;

import org.bapp.model.User;

import java.util.List;

public interface UserService {
    User findById(Long id);
    List<User> findAll();
    void delete(Long id);
    User findByUsername(String username);
    User findByUsernameAndEmail(String username, String email);
    List<User> findByEnabled(boolean enabled);
    User createUser(User user);
}
