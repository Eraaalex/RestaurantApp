package org.hse.software.construction.restaurantapp.utility;


import lombok.AllArgsConstructor;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.service.DishService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class DishConverter implements Converter<UUID, Dish> {
    private final DishService dishService;

    @Override
    public Dish convert(UUID source) {
        return dishService.findById(source);
    }
}
