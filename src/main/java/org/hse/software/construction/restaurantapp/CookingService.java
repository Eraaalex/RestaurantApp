package org.hse.software.construction.restaurantapp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.model.Order;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.model.Status;
import org.hse.software.construction.restaurantapp.service.DishService;
import org.hse.software.construction.restaurantapp.service.BucketService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;

@Service
@Slf4j
@AllArgsConstructor
public class CookingService {
    private DishService dishService;
    private BucketService bucketService;
    private final ExecutorService cookExecutor = Executors.newFixedThreadPool(3);

    private final Map<UUID, List<Future<?>>> cookingTasks = new ConcurrentHashMap<>();

    public void processOrder(Order order) {
        order.setStatus(Status.IN_PROGRESS);
        bucketService.updateOrder(order);
        List<Future<?>> tasks = new ArrayList<>();

        order.getSelectedDishes().forEach((dishId, amount) -> {
            for (int i = 0; i < amount; i++) {
                Dish dish = dishService.findById(dishId);
                Future<?> future = cookExecutor.submit(() -> {
                    cookDish(dish);
                });
                tasks.add(future);
            }
        });

        cookingTasks.put(order.getId(), tasks);
        checkOrderCompletion(order);
    }

    public void addDishToOrder(UUID orderId, UUID dishId, int quantity) {

        Order order = bucketService.findById(orderId);
        Dish dish = dishService.findById(dishId);
        log.info("[ADD] in Order " + orderId + "dish:" + dish.getName() + quantity + " to cook!");
        order.addDish(dishId, quantity, dish.getPrice());
        bucketService.updateOrder(order);

        List<Future<?>> tasks = cookingTasks.getOrDefault(orderId, new ArrayList<>());

        if (tasks == null) {
            tasks = new ArrayList<>();
            cookingTasks.put(orderId, tasks);
        }
        for (int i = 0; i < quantity; i++) {
            Future<?> future = cookExecutor.submit(() -> {
                log.info("[ADD] Start cooking " + dish.getName() + " " + LocalTime.now());
                cookDish(dish);
                log.info("[ADD] COOKED " + dish.getName() + " cooked!" + LocalTime.now());
            });
            tasks.add(future);
        }


        checkOrderCompletion(order);
    }

    private void cookDish(Dish dish) {
        try {
            TimeUnit.SECONDS.sleep(dish.getTime());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    private void checkOrderCompletion(Order order) {
        cookExecutor.submit(() -> {
            List<Future<?>> tasks = cookingTasks.get(order.getId());

            boolean allDone = tasks.stream().allMatch(Future::isDone);
            while (!allDone) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    allDone = tasks.stream().allMatch(Future::isDone);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            completeOrder(order);
            cookingTasks.remove(order.getId());
        });
    }


    private void completeOrder(Order order) {
        order.setStatus(Status.COMPLETED);
        bucketService.updateOrder(order);
        log.info("Order " + order.getId() + " completed!");
    }
    public synchronized void cancelOrder(UUID orderId) {

        Order order = bucketService.findById(orderId);
        order.setStatus(Status.CANCELED);
        bucketService.updateOrder(order);
        log.info("UPDATED order " + orderId);
        List<Future<?>> tasks = cookingTasks.get(orderId);
        if (tasks != null) {
            tasks.forEach(future -> future.cancel(true));
        }
        cookingTasks.remove(orderId);
        log.info("REMOED order " + orderId);
    }


}
