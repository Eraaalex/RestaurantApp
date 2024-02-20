package org.hse.software.construction.restaurantapp.service.impl.user;

import lombok.AllArgsConstructor;
import org.hse.software.construction.restaurantapp.model.Human;
import org.hse.software.construction.restaurantapp.repository.UserRepository;
import org.hse.software.construction.restaurantapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void addUser(Human user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }
}
