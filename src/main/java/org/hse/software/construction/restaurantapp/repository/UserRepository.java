package org.hse.software.construction.restaurantapp.repository;

import org.hse.software.construction.restaurantapp.model.Human;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Human, UUID> {

    Optional<Human> findByName(String username);
}
