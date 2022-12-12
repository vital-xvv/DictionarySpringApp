package com.spring.dictionary.service;

import com.spring.dictionary.actors.Role;
import com.spring.dictionary.actors.User;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getAllUsers();
    void deleteUserByUsername(String username);
    Page<User> findUsersWithPagination(int offset, int pageSize);

    List<User> findUsersWithFiltering(String field);

    Page<User> findUsersWithPaginationAndFiltering(int offset, int pageSize, String field);

    @Transactional
    void registerNewUser(User user);
}
