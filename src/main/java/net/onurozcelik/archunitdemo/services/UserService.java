package net.onurozcelik.archunitdemo.services;

import java.util.Set;

import net.onurozcelik.archunitdemo.models.User;

public interface UserService {
    Set<User> getUsers();
}
