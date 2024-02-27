package org.hse.software.construction.restaurantapp.service.impl.dish;


import lombok.AllArgsConstructor;
import org.apache.catalina.valves.StuckThreadDetectionValve;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.repository.DishRepository;
import org.hse.software.construction.restaurantapp.service.DishService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@AllArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository repository;

    @Override
    public List<Dish> findAllDish() {
        return repository.findAll();
    }

    @Override
    public Dish saveDish(Dish dish) {
        return repository.save(dish);
    }

    @Override
    public Dish findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Dish updateDish(Dish dish) {
        Optional<Dish> existingDish = repository.findById(dish.getId());
        if (existingDish.isPresent()) {
            Dish updatedDish = existingDish.get();
            // Update fields
            updatedDish.setName(dish.getName());
            updatedDish.setPrice(dish.getPrice());
            updatedDish.setAmount(dish.getAmount());
            // Save the updated entity
            return repository.save(updatedDish);
        }
        return null;
    }

    @Override
    public void deleteDish(String name) {
        repository.deleteByName(name);
    }

    @Override
    public Dish findById(UUID dishId) {
        return repository.findById(dishId).orElse(null);
    }
}
