package com.spring.dictionary.service;

import com.spring.dictionary.actors.Role;
import com.spring.dictionary.actors.User;
import com.spring.dictionary.repository.RoleRepository;
import com.spring.dictionary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Saving new user {} to the database", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            log.error("User {} not found in the databse", username);
            throw new UsernameNotFoundException("User not found in the databse");
        }
        else {
            log.info("User {} found in the database     {}", username, LocalDateTime.now());
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding a role {} to a user {}", roleName, username);
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("Getting a user {} from the database", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public void deleteUserByUsername(String username) {
        log.info("Deleting user "+ username);
        userRepository.deleteUserByUsername(username);
    }

    @Override
    public Page<User> findUsersWithPagination(int offset, int pageSize) {
        Pageable pageable = PageRequest.of(offset, pageSize);
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> findUsersWithFiltering(String field) {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    @Override
    public Page<User> findUsersWithPaginationAndFiltering(int offset, int pageSize, String field) {
        Pageable pageable = PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.ASC, field));
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void registerNewUser(User user) {
        saveUser(user);
        addRoleToUser(user.getUsername(), "ROLE_USER");
        log.info("Saving new user {} to the database with role {}", user.getUsername(), "ROLE_USER");
    }

    public User findUserInfoByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void changeUserPasswordByUsername(String username, String oldPassword, String newPassword){
        User userInfo = userRepository.findByUsername(username);
        boolean passwordsCorrespond = passwordEncoder.matches(oldPassword, userInfo.getPassword());
        if(passwordsCorrespond) userInfo.setPassword(passwordEncoder.encode(newPassword));
        log.info("Changed password for user {}.", userInfo.getUsername());
    }

    public void updateUserFirstNameAndLastNameByUsername(String firstName, String lastName, String username){
        userRepository.updateUserFirstNameAndLastNameByUsername(firstName, lastName, username);
    }


}
