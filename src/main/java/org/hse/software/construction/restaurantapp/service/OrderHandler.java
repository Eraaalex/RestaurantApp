package org.hse.software.construction.restaurantapp.service;

import jakarta.persistence.OptimisticLockException;
import lombok.AllArgsConstructor;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class OrderHandler {

    private final DishService dishService;
    @Transactional
    public double checkOrder(Map<UUID, Integer> selectedDishes) {
        int maxRetries = 3;
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try {
                return checkOrderInternal(selectedDishes);
            } catch (OptimisticLockException e) {
                retryCount++;
            } catch (RuntimeException e) {
                return 0.0;
            }
        }
        return 0.0;
    }

    private double checkOrderInternal(Map<UUID, Integer> selectedDishes) {
        AtomicReference<Double> totalCost = new AtomicReference<>(0.0);
        selectedDishes.forEach((dishId, quantity) -> {
            Dish dish = dishService.findById(dishId);
            totalCost.updateAndGet(v -> v + dish.getPrice() * quantity);
        });
        return totalCost.get();
    }
}
