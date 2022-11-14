package net.onurozcelik.archunitdemo.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.onurozcelik.archunitdemo.models.User;
import net.onurozcelik.archunitdemo.services.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/users")
    public Set<User> getUsers() {
        return userService.getUsers();
    }
}
