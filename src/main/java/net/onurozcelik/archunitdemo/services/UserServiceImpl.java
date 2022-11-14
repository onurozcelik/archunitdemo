package net.onurozcelik.archunitdemo.services;

import net.onurozcelik.archunitdemo.entities.UserEntity;
import net.onurozcelik.archunitdemo.models.User;
import net.onurozcelik.archunitdemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Set<User> getUsers() {
        Set<User> users = new HashSet<>();
        Iterable<UserEntity> iterable = userRepository.findAll();
        iterable.forEach(u -> users.add(new User(u.getId(), u.getUsername())));
        return users;
    }

}
