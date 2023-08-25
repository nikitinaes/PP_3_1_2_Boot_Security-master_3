package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService  {
    List<User> findAllUsers ();
    User getUserById(long id);
    User saveUser(User user);
    boolean removeUser(Long userId);
    User updateUser(User user);

}
