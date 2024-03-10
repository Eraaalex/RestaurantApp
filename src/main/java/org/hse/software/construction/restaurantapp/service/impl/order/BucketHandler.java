package org.hse.software.construction.restaurantapp.service.impl.order;

import jakarta.persistence.OptimisticLockException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.service.DishService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@AllArgsConstructor
public class BucketHandler {
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
                log.info("[checkOrder] OptimisticLockException!");
            } catch (RuntimeException e) {
                log.info("Exception");
                return 0.0;
            }
        }
        log.info("[checkOrder] ERROR total cost is 0.0");
        return 0.0;
    }

    private double checkOrderInternal(Map<UUID, Integer> selectedDishes) {
        AtomicReference<Double> totalCost = new AtomicReference<>(0.0);
        selectedDishes.forEach((dishId, quantity) -> {
            Dish dish = dishService.findById(dishId);
            int availability = dish.getAmount();
            if (quantity <= availability) {
                totalCost.updateAndGet(v -> v + dish.getPrice() * quantity);
                dish.setAmount(availability - quantity);
                dishService.updateDish(dish);
            }

        });
        return totalCost.get();
    }

    @Transactional
    public boolean reserveDish(UUID dishId, int quantity) {
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

    private void attemptToAddDishesToOrder(UUID dishId, int quantity) {
        Dish dish = dishService.findById(dishId);
        if (quantity > dish.getAmount()) {
            log.info("Insufficient dish quantity available");
            return;
        }

        dish.setAmount(dish.getAmount() - quantity);
        dishService.updateDish(dish);
    }


}
