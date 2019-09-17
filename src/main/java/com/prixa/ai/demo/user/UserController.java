package com.prixa.ai.demo.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/all")
    public Map<String, User> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping(value = "/addUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        user = userService.createUser(user.getFullName());
        logger.info(user.toString());
        return user;
    }

    @DeleteMapping(value = "/deleteUser/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }
}
