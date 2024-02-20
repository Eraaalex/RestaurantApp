package org.hse.software.construction.restaurantapp.service;


import org.hse.software.construction.restaurantapp.model.Dish;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DishService {
    List<Dish> findAllDish();

    Dish saveDish(Dish dish);
    Dish findByName(String name);

    Dish updateDish (Dish dish);
    void deleteDish (String name);
    
}
