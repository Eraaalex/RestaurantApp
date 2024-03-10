package org.hse.software.construction.restaurantapp.repository;

import org.hse.software.construction.restaurantapp.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DishRepository extends JpaRepository<Dish, UUID> {
    void deleteByName(String dishName);

    Optional<Dish> findByName(String dishName);
}
