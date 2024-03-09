package org.hse.software.construction.restaurantapp.service.impl;

import lombok.AllArgsConstructor;
import org.hse.software.construction.restaurantapp.model.Order;
import org.hse.software.construction.restaurantapp.service.BucketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class BucketServiceImpl implements BucketService {
    List<Order> buckets;

    @Override
    public List<Order> findAllOrders() {
        return buckets;
    }

    @Override
    public Order saveOrder(Order order) {

        buckets.add(order);
        return order;
    }

    @Override
    public Order findById(UUID id) {
        return buckets.stream().filter(order -> order.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Order updateOrder(Order order) {
        buckets.removeIf(bucket -> bucket.getId().equals(order.getId()));
        buckets.add(order);
        return order;
    }

    @Override
    public void deleteOrder(UUID id) {
        buckets.removeIf(order -> order.getId().equals(id));
    }


}
