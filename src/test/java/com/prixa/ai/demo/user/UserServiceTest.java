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

import static com.prixa.ai.demo.utils.MessagesConstant.USER_NOT_FOUND;
import static com.prixa.ai.demo.utils.MessagesConstant.USER_SUCCESS_DELETED;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DemoApplicationTestConfig.class)
public class UserServiceTest {

    private final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private UserService userService;

    @Test
    public void createUser() {
        String fullName = "Azmi Basyir";
        User user = userService.createUser(fullName);
        System.out.println(user.toString());
    }

    @Test
    public void createUserOnlyFirstName() {
        String fullName = "Azmi";
        User user = userService.createUser(fullName);
        System.out.println(user.toString());
    }

    @Test(expected = NullPointerException.class)
    public void createUserWhenFullNameNull() {
        String fullName = null;
        User user = userService.createUser(fullName);
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
        String result = userService.deleteUser(userMap.get("azmi.basyir").getUsername());
        logger.info(result);
        assertEquals(result, USER_SUCCESS_DELETED);
        logger.info("======================after delete =============================");
        userMap = userService.findAll();
        logger.info(userMap.toString());
    }

    @Test
    public void deleteUserNotExist() {
        generateUser();
        Map<String, User> userMap = userService.findAll();
        logger.info(userMap.toString());
        String result = userService.deleteUser("marpaung");
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
