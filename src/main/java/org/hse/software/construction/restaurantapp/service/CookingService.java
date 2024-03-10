package org.hse.software.construction.restaurantapp.service;

import org.hse.software.construction.restaurantapp.model.Cart;
import org.hse.software.construction.restaurantapp.model.Dish;

import java.util.UUID;

public interface CookingService {

    void processOrder(Cart cart);

    void addDishToOrder(UUID orderId, UUID dishId, int quantity);

    void cookDish(Dish dish);

    void cancelOrder(UUID orderId);

}
