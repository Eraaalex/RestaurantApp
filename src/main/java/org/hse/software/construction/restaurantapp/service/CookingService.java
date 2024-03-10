package org.hse.software.construction.restaurantapp.service;

import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.model.Cart;

import java.util.UUID;

public interface CookingService {

    public void processOrder(Cart cart);

    public void addDishToOrder(UUID orderId, UUID dishId, int quantity);

    public void cookDish(Dish dish);

    public void cancelOrder(UUID orderId);

}
