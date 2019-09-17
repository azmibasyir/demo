package com.prixa.ai.demo;

import com.prixa.ai.demo.user.UserService;
import com.prixa.ai.demo.user.UserServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DemoApplicationTestConfig {
    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
}
