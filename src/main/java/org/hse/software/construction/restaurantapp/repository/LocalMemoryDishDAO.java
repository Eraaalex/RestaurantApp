package org.hse.software.construction.restaurantapp.repository;

import org.hse.software.construction.restaurantapp.model.Dish;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Repository
public class LocalMemoryDishDAO {
    
    private final List<Dish> dishes = new ArrayList<>();


    public List<Dish> findAllDishes() {
        return dishes;

    }


    public Dish saveDish(Dish dish) {
        dishes.add(dish);
        return dish;
    }


    public Dish findByName(String name) {
        return dishes.stream().filter(dish -> dish.getName().equals(name))
                .findFirst()
                .orElse(null);
    }


    public Dish updateDish(Dish dish) {
        var dishIndex = IntStream.range(0, dishes.size())
                .filter(index -> dishes.get(index).getName().equals(dish.getName()))
                .findFirst().orElse(-1);
        if (dishIndex > -1){
            dishes.set(dishIndex, dish);
            return dish;
        }
        return null;

    }


    public void deleteDish(String name) {
        var dish = findByName(name);
        if (dish != null){
            dishes.remove(dish);
        }
    }

    public Dish findById(UUID dishId) {
        return dishes.stream().filter(dish -> dish.getId().equals(dishId))
                .findFirst()
                .orElse(null);
    }
}
