package org.hse.software.construction.restaurantapp.repository;

import org.hse.software.construction.restaurantapp.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface DishRepository extends JpaRepository<Dish, UUID> {
    void deleteByName(String dishName);
    Dish findByName(String dishName);
}
