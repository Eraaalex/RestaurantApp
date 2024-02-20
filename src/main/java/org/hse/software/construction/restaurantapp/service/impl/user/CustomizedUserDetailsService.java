package org.hse.software.construction.restaurantapp.service.impl.user;


import org.hse.software.construction.restaurantapp.config.CustomizedUserDetails;
import org.hse.software.construction.restaurantapp.model.Human;
import org.hse.software.construction.restaurantapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomizedUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Human> user = repository.findByName(username);
        return user.map(CustomizedUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}
