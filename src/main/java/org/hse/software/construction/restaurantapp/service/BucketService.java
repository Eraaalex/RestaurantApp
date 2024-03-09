package org.hse.software.construction.restaurantapp.service;

import org.hse.software.construction.restaurantapp.model.Order;

import java.util.List;
import java.util.UUID;

public interface BucketService {

    List<Order> findAllOrders();

    Order saveOrder(Order order);

    Order findById(UUID id);

    Order updateOrder(Order order);

    void deleteOrder(UUID id);

}
