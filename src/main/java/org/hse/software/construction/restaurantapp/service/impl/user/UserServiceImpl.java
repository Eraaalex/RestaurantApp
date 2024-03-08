package org.hse.software.construction.restaurantapp.service.impl.user;

import lombok.AllArgsConstructor;
import org.hse.software.construction.restaurantapp.model.Human;
import org.hse.software.construction.restaurantapp.model.Role;
import org.hse.software.construction.restaurantapp.repository.RoleRepository;
import org.hse.software.construction.restaurantapp.repository.UserRepository;
import org.hse.software.construction.restaurantapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Override
    public void addUser(Human user) {
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = new Role();
            role.setName("ROLE_USER");
            roleRepository.save(role);
            role = new Role();
            role.setName("ROLE_ADMIN");
            roleRepository.save(role);
        }
        Role role1 = roleRepository.findByName("ROLE_USER");
        Role role2 = roleRepository.findByName("ROLE_ADMIN");
        user.getRoles().add(role1);
        user.getRoles().add(role2);

        userRepository.save(user);
    }

    @Override
    public Human findById(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }


    @Override
    public List<Human> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Human findByName(String name) {
        return userRepository.findByName(name).orElse(null);
    }

}
