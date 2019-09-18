package com.prixa.ai.demo.user;

import com.prixa.ai.demo.DemoApplicationTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.prixa.ai.demo.utils.MessagesConstant.USER_DELETION_SUCCESS;
import static com.prixa.ai.demo.utils.MessagesConstant.USER_NOT_FOUND;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DemoApplicationTestConfig.class)
public class UserServiceTest {

    private final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
    private final String USERNAME = "azmi.basyir";
    private final String USERNAME_NOT_FOUND = "marpaung";
    private final String FULLNAME = "azmi basyir";
    private final String FIRST_NAME = "azmi";


    @Autowired
    private UserService userService;

    @Test
    public void createUser() {
        User user = userService.createUser(FULLNAME);
        logger.info(user.toString());
    }

    @Test
    public void createUserOnlyFirstName() {
        User user = userService.createUser(FIRST_NAME);
        logger.info(user.toString());
    }

    @Test(expected = NullPointerException.class)
    public void createUserWhenFullNameNull() {
        userService.createUser(null);
    }

    @Test
    public void fetchingAllUser() {
        generateUser();
        Map<String, User> userMap = userService.findAll();
        logger.info(userMap.toString());
    }

    @Test
    public void deleteUserExist() {
        generateUser();
        Map<String, User> userMap = userService.findAll();
        logger.info(userMap.toString());
        String result = userService.deleteUser(userMap.get(USERNAME).getUsername());
        logger.info(result);
        assertEquals(result, USER_DELETION_SUCCESS);
        logger.info("======================after delete =============================");
        userMap = userService.findAll();
        logger.info(userMap.toString());
    }

    @Test
    public void deleteUserNotExist() {
        generateUser();
        Map<String, User> userMap = userService.findAll();
        logger.info(userMap.toString());
        String result = userService.deleteUser(USERNAME_NOT_FOUND);
        logger.info(result);
        assertEquals(result, USER_NOT_FOUND);
        logger.info("======================after delete =============================");
        userMap = userService.findAll();
        logger.info(userMap.toString());
    }

    private void generateUser() {
        List<String> fullNameLst = new ArrayList<>();
        fullNameLst.add("azmi basyir");
        fullNameLst.add("azmi marpaung");
        fullNameLst.add("marpaung azmi");

        for (String fullName : fullNameLst) {
            userService.createUser(fullName);
        }
    }
}
