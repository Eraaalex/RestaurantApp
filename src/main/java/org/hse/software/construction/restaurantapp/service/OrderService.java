package org.hse.software.construction.restaurantapp.service;

import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.model.Order;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderService {

    List<Order> findAllOrder();

    Order saveOrder(Order order);

    Order findById(UUID id);

    Order updateOrder(Order order);

    void deleteOrder(UUID id);

}
