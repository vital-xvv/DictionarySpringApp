package com.spring.dictionary.service;

import com.spring.dictionary.actors.Role;
import com.spring.dictionary.actors.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getAllUsers();
    void deleteUser(User user);
}
