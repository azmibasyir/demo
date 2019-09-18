package com.prixa.ai.demo.user;

import java.util.Map;

public interface UserService {

    User createUser(String fullname);

    Map<String, User> findAll();

    String deleteUser(String username);

}
