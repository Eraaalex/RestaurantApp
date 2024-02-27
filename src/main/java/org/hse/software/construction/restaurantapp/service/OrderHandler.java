package org.hse.software.construction.restaurantapp.service;

import jakarta.persistence.OptimisticLockException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.model.Order;
import org.hse.software.construction.restaurantapp.model.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
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
                log.info("OptimisticLockException");
            } catch (RuntimeException e) {
                log.info("Exception");
                return 0.0;
            }
        }
        log.info("WRONG");
        return 0.0;
    }

    private double checkOrderInternal(Map<UUID, Integer> selectedDishes) {
        AtomicReference<Double> totalCost = new AtomicReference<>(0.0);
        selectedDishes.forEach((dishId, quantity) -> {
            Dish dish = dishService.findById(dishId);
            int availability = dish.getAmount();
            if (quantity <= availability){
                totalCost.updateAndGet(v -> v + dish.getPrice() * quantity);
                dish.setAmount(availability - quantity);
                dishService.updateDish(dish);
            }

        });
        return totalCost.get();
    }

    @Transactional
    public boolean checkToAddDish( UUID dishId, int quantity) {
        final int maxRetries = 3;
        int attempts = 0;

        while (maxRetries > attempts) {
            try {
                attemptToAddDishesToOrder(dishId, quantity);
                return true;
            } catch (OptimisticLockException ole) {
                if (++attempts == maxRetries) {
                    return false;
                }

            }
        }
        return false;
    }

    private void attemptToAddDishesToOrder( UUID dishId, int quantity) {
        Dish dish = dishService.findById(dishId);
        if (quantity > dish.getAmount()) {
            throw new IllegalArgumentException("Insufficient dish quantity available");
        }

        dish.setAmount(dish.getAmount() - quantity);
        dishService.updateDish(dish);
    }
}
