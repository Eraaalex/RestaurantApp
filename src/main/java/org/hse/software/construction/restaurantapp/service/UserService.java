package org.hse.software.construction.restaurantapp.service;

import org.hse.software.construction.restaurantapp.model.Human;

import java.util.List;
import java.util.UUID;

//public interface UserService {
//    void addUser(Human user, Boolean isAdmin);
//    Human findById(UUID userId);
//    List<Human> findAllUsers();
//    Human findByName(String name);
//}

import java.util.UUID;

public interface UserService {
    void addUser(Human user, Boolean isAdmin);
    Human findById(UUID userId);
    List<Human> findAllUsers();
    Human findByName(String name);
}