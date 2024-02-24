package org.hse.software.construction.restaurantapp.service.impl;

import lombok.AllArgsConstructor;
import org.hse.software.construction.restaurantapp.model.Order;
import org.hse.software.construction.restaurantapp.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    List<Order> orders;
    @Override
    public List<Order> findAllOrder() {
        return orders;
    }

    @Override
    public Order saveOrder(Order order) {

        orders.add(order);
        return order;
    }

    @Override
    public Order findById(UUID id) {
        return orders.stream().filter(order -> order.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Order updateOrder(Order order) {
        orders.removeIf(o -> o.getId().equals(order.getId()));
        orders.add(order);
        return order;
    }

    @Override
    public void deleteOrder(UUID id) {
        orders.removeIf(order -> order.getId().equals(id));
    }

}
