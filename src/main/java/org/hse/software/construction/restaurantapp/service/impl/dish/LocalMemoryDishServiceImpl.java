package org.hse.software.construction.restaurantapp.service.impl.dish;


import lombok.AllArgsConstructor;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.repository.LocalMemoryDishDAO;
import org.hse.software.construction.restaurantapp.service.DishService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LocalMemoryDishServiceImpl implements DishService {
    private final LocalMemoryDishDAO repository;

    @Override
    public List<Dish> findAllDish() {
        return repository.findAllDishes();
    }

    @Override
    public Dish saveDish(Dish dish) {
        return repository.saveDish(dish);
    }

    @Override
    public Dish findByName(String name) {
        return repository.findByName(name);
    }


    @Override
    public Dish updateDish(Dish dish) {
        return repository.updateDish(dish);
    }

    @Override
    public void deleteDishByName(String name) {
        repository.deleteDish(name);
    }

    @Override
    public void deleteDish(UUID id) {
        repository.deleteDish(findById(id).getName());
    }

    @Override
    public Dish findById(UUID dishId) {
        return repository.findById(dishId);
    }
}
