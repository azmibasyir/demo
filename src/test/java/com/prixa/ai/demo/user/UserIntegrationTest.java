package com.prixa.ai.demo.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.prixa.ai.demo.DemoApplicationTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static com.prixa.ai.demo.utils.MessagesConstant.USER_DELETION_SUCCESS;
import static com.prixa.ai.demo.utils.MessagesConstant.USER_NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = DemoApplicationTestConfig.class)
public class UserIntegrationTest {

    private final Logger logger = LoggerFactory.getLogger(UserIntegrationTest.class);
    private final String USERNAME = "azmi.basyir";
    private final String FIRST_NAME = "azmi";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Test
    public void createUser() throws Exception {
        User user = new User();
        user.setFullName(USERNAME);
        ObjectWriter ow = initialObjectWriter();
        String requestJson = ow.writeValueAsString(user);

        mockMvc.perform(
                post("/users/addUser")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{}"));
    }

    @Test
    public void createUser_firstName() throws Exception {
        User user = new User();
        user.setFullName(FIRST_NAME);
        ObjectWriter ow = initialObjectWriter();
        String requestJson = ow.writeValueAsString(user);

        mockMvc.perform(
                post("/users/addUser")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{}"));
    }

    @Test
    public void createUserWhenFullNameNull() throws Exception {
        User user = new User();
        user.setFullName(null);
        ObjectWriter ow = initialObjectWriter();
        String requestJson = ow.writeValueAsString(user);

        mockMvc.perform(
                post("/users/addUser")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void fetchingAllUser() throws Exception {

        generateUser();
        fetchAllUser();

    }

    @Test
    public void deleteUserExist() throws Exception {
        generateUser();
        logger.info("################BEFORE DELETE#####################");
        fetchAllUser();

        ResultActions result = mockMvc.perform(
                delete("/users/deleteUser/azmi.basyir")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        logger.info("################AFTER DELETE#####################");
        fetchAllUser();

        assertEquals(result.andReturn().getResponse().getContentAsString(), USER_DELETION_SUCCESS);
    }

    @Test
    public void deleteUserNotExist() throws Exception {
        logger.info("################BEFORE DELETE#####################");
        fetchAllUser();
        ResultActions result = mockMvc.perform(
                delete("/users/deleteUser/azmi.satu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        logger.info("################AFTER DELETE#####################");
        fetchAllUser();

        assertEquals(result.andReturn().getResponse().getContentAsString(), USER_NOT_FOUND);
    }

    private ObjectWriter initialObjectWriter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow;
    }

    private void generateUser() {
        List<String> fullNameLst = new ArrayList<>();
        fullNameLst.add("azmi basyir");
        fullNameLst.add("azmi marpaung");
        fullNameLst.add("marpaung azmi");

        for (String fullName : fullNameLst) {
            userController.createUser(new User(fullName));
        }
    }

    private void fetchAllUser() throws Exception {
        mockMvc.perform(
                get("/users/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"))
                .andDo(MockMvcResultHandlers.print());
    }
}
