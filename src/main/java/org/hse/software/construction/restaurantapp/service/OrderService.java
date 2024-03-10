package org.hse.software.construction.restaurantapp.service;

import org.hse.software.construction.restaurantapp.model.Cart;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<Cart> findAllOrders();

    Cart saveOrder(Cart cart);

    Cart findById(UUID id);

    Cart updateOrder(Cart cart);

    void deleteOrder(UUID id);

}
