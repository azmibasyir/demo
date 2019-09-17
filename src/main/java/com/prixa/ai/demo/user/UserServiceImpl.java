package com.prixa.ai.demo.user;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.prixa.ai.demo.utils.MessagesConstant.*;

@Service
@CacheConfig(cacheNames = {"users"})
public class UserServiceImpl implements UserService{

    private Map<String, User> userMap = new HashMap();
    private final String MAIL = "@kalimat.ai";

    @Cacheable
    @Override
    public Map<String, User> findAll() {
        simulateSlowService();
        return this.userMap;
    }

    @Override
    public User createUser(String fullname) {
        String[] name = fullname.split(" ");
        String username;
        User user;
        if (name.length == 1) {
            username = name[0].toLowerCase();
            user = validateUser(username);
        } else {
            username = name[0].concat(".").concat(name[name.length - 1]);
            user = validateUser(username);
        }
        user.setFullName(fullname);
        this.userMap.put(user.getUsername(), user);
        return user;
    }

    @Override
    public String deleteUser(String username) {
        boolean exist = checkUserExist(username);
        String result;
        if(exist){
            this.userMap.remove(username);
            result = USER_SUCCESS_DELETED;
        } else
            result = USER_NOT_FOUND;
        return result;
    }

    private boolean checkUserExist(String username){
        boolean exist=false;
        if(this.userMap.get(username) != null){
            exist = true;
        }
        return exist;
    }

    private void simulateSlowService() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private User validateUser(String username) {
        User user = userMap.get(username);
        if (user != null) {
            username = username.concat(String.valueOf(user.getCount() + 1));
            user.setCount(user.getCount() + 1);
        } else {
            user = new User();
            user.setCount(1);
        }
        user.setEmail(username.concat(MAIL));
        user.setUsername(username);

        return user;
    }
}
