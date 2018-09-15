package org.bapp.web.controller.admin.security;

import org.bapp.model.User;
import org.bapp.services.user.UserService;
import org.bapp.services.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("admin/system/user")
public class UserController {

    @Autowired
    private UserService userService = new UserServiceImpl();

    @PostMapping("create")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws URISyntaxException {

        User newUser = userService.createUser(user);

        return ResponseEntity.created(new URI("users/" + newUser.getUsername()))
                .header(newUser.getUsername())
                .body(newUser);
    }

    @GetMapping("/user")
    public User getUser(@RequestParam(value="username") String username){
        return userService.findByUsername(username);
    }

    @GetMapping("/all")
    public List<User> userList(){
        return userService.findAll();
    }

}
