package com.spring.dictionary.controller;

import com.spring.dictionary.actors.User;
import com.spring.dictionary.entity.PasswordDTO;
import com.spring.dictionary.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@CrossOrigin("http://localhost:3000")
public class RegisteredUsersController {

    private final UserServiceImpl userService;

    @Autowired
    public RegisteredUsersController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public User getUserInfoByUsername(@PathVariable String username){
        return userService.findUserInfoByUsername(username);
    }

    @PutMapping("/{username}/change/password")
    public void changePassword(@PathVariable String username, @RequestBody PasswordDTO passwordDTO){
        userService.changeUserPasswordByUsername(username, passwordDTO.getOldRawPassword(), passwordDTO.getNewRawPassword());
    }

    @PutMapping("/update/first/last/name")
    public void updateUserFirstAndLastName(@RequestBody User user) {
        userService.updateUserFirstNameAndLastNameByUsername(user.getFirstName(), user.getLastName(), user.getUsername());
    }
}
