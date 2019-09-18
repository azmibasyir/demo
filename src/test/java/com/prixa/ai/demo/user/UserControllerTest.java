package com.prixa.ai.demo.user;

import com.prixa.ai.demo.DemoApplicationTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

import static com.prixa.ai.demo.utils.MessagesConstant.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = DemoApplicationTestConfig.class)
public class UserControllerTest {

    private final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
    private final String USERNAME = "azmi.basyir";
    private final String USERNAME_NOT_FOUND = "marpaung";
    private final String FULLNAME = "azmi basyir";
    private final String FIRST_NAME = "azmi";

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void createUser() {
        User user = generateUser(FULLNAME);
        User usr = new User();
        usr.setFullName(FULLNAME);
        when(userService.createUser(any(String.class)))
                .thenReturn(user);
        user = userController.createUser(usr);
        logger.info(user.toString());
    }

    @Test
    public void createUserOnlyFirstName() {
        User user = generateUser(FIRST_NAME);
        User usr = new User();
        usr.setFullName(FIRST_NAME);
        when(userService.createUser(any(String.class)))
                .thenReturn(user);
        user = userController.createUser(usr);
        logger.info(user.toString());
    }

    @Test(expected = NullPointerException.class)
    public void createUserWhenFullNameNull() {
        User user = generateUser(null);
        when(userService.createUser(any(String.class)))
                .thenReturn(user);
        userController.createUser(null);
    }

    @Test
    public void fetchingAllUser() {
        when(userService.findAll())
                .thenReturn(generateUsers());
        Map<String, User> userMap = userController.getAllUsers();
        logger.info(userMap.toString());
    }

    @Test
    public void deleteUserExist() {
        when(userService.deleteUser(USERNAME))
                .thenReturn(USER_DELETION_SUCCESS);
        String result = userController.deleteUser(USERNAME);
        logger.info(result);
        assertEquals(result, USER_DELETION_SUCCESS);
    }

    @Test
    public void deleteUserNotExist() {
        when(userService.deleteUser(USERNAME_NOT_FOUND))
                .thenReturn(USER_NOT_FOUND);
        String result = userController.deleteUser(USERNAME_NOT_FOUND);
        logger.info(result);
        assertEquals(result, USER_NOT_FOUND);
    }

    private User generateUser(String fullname) {
        String[] name = fullname.split(" ");
        String username;
        User user = new User();
        if (name.length == 1) {
            username = name[0].toLowerCase();
        } else {
            username = name[0].concat(".").concat(name[name.length - 1]);
        }
        user.setFullName(fullname);
        user.setEmail(username.concat(MAIL));
        user.setUsername(username);
        return user;
    }

    public Map<String, User> generateUsers() {
        Map<String, User> userMap = new HashMap<>();
        userMap.put("azmi.basyir", new User("azmi basyir", "azmi.basyir", "azmi.basyir@kalimat.ai", 1));
        userMap.put("azmi.marpaung", new User("azmi marpaung", "azmi.marpaung", "azmi.marpaung@kalimat.ai", 1));
        userMap.put("basyir.marpaung", new User("basyir marpaung", "basyir marpaung", "basyir marpaung@kalimat.ai", 1));

        return userMap;
    }
}

