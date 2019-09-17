package com.prixa.ai.demo.user;

import javax.validation.constraints.NotNull;

public class User {

    @NotNull(message = "full name not yet filled")
    private String fullName;
    private String username;
    private String email;
    private Integer count;

    public User() {
        //
    }

    public User(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
