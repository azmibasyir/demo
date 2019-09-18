package com.prixa.ai.demo.user;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static com.prixa.ai.demo.utils.MessagesConstant.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "fetching all user generated",
            response = User.class,
            responseContainer = "List")
    @GetMapping(value = "/all")
    public Map<String, User> getAllUsers() {
        return userService.findAll();
    }

    @ApiOperation(value = "Generate new user",
            response = User.class,
            responseContainer = "User")
    @ApiResponses(value = {@ApiResponse(code = 201, message = USER_SUCCESSFULLY_CREATED), @ApiResponse(code = 400, message = INVALID_FULLNAME)})
    @PostMapping(value = "/addUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        user = userService.createUser(user.getFullName());
        logger.info(user.toString());
        return user;
    }

    @ApiOperation(value = "delete user",
            response = User.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = USER_DELETION_SUCCESS), @ApiResponse(code = 400, message = INVALID_USERNAME)})
    @DeleteMapping(value = "/deleteUser/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }
}
