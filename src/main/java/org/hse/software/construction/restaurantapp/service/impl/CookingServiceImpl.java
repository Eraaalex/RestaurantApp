package org.hse.software.construction.restaurantapp.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.OrderCompletedEvent;
import org.hse.software.construction.restaurantapp.model.Cart;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.model.Status;
import org.hse.software.construction.restaurantapp.service.CookingService;
import org.hse.software.construction.restaurantapp.service.DishService;
import org.hse.software.construction.restaurantapp.service.CartService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;

@Service
@Slf4j
@AllArgsConstructor
public class CookingServiceImpl implements CookingService {
    private DishService dishService;
    private CartService cartService;
    private final ExecutorService cookExecutor = Executors.newFixedThreadPool(3);

    private final Map<UUID, List<Future<?>>> cookingTasks = new ConcurrentHashMap<>();

    private ApplicationEventPublisher eventPublisher;

    public void processOrder(Cart cart) {
        cart.setStatus(Status.IN_PROGRESS);
        cartService.updateBucket(cart);
        List<Future<?>> tasks = new ArrayList<>();

        cart.getSelectedDishes().forEach((dishId, amount) -> {
            for (int i = 0; i < amount; i++) {
                Dish dish = dishService.findById(dishId);
                Future<?> future = cookExecutor.submit(() -> {
                    cookDish(dish);
                });
                tasks.add(future);
            }
        });

        cookingTasks.put(cart.getId(), tasks);
        checkOrderCompletion(cart);
    }

    public void addDishToOrder(UUID orderId, UUID dishId, int quantity) {

        Cart cart = cartService.findById(orderId);
        Dish dish = dishService.findById(dishId);
        log.info("[ADD] in Order " + orderId + "dish:" + dish.getName() + quantity + " to cook!");
        cart.addDish(dishId, quantity, dish.getPrice());
        cartService.updateBucket(cart);

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


        checkOrderCompletion(cart);
    }

    public void cookDish(Dish dish) {
        try {
            TimeUnit.SECONDS.sleep(dish.getTime());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    private void checkOrderCompletion(Cart cart) {
        cookExecutor.submit(() -> {
            List<Future<?>> tasks = cookingTasks.get(cart.getId());

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
            completeOrder(cart);
            cookingTasks.remove(cart.getId());
        });
    }


    private void completeOrder(Cart cart) {
        cart.setStatus(Status.COMPLETED);
        cartService.updateBucket(cart);
        log.info("Order " + cart.getId() + " completed!");

        OrderCompletedEvent event = new OrderCompletedEvent(this, cart);
        eventPublisher.publishEvent(event);
    }
    public synchronized void cancelOrder(UUID orderId) {

        Cart cart = cartService.findById(orderId);
        cart.setStatus(Status.CANCELED);
        cartService.updateBucket(cart);
        log.info("UPDATED order " + orderId);
        List<Future<?>> tasks = cookingTasks.get(orderId);
        if (tasks != null) {
            tasks.forEach(future -> future.cancel(true));
        }
        cookingTasks.remove(orderId);
        log.info("REMOVED order " + orderId);
    }
}
