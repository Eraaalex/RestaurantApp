package org.hse.software.construction.restaurantapp.service.impl.order;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.model.Cart;
import org.hse.software.construction.restaurantapp.model.Order;
import org.hse.software.construction.restaurantapp.repository.OrderRepository;
import org.hse.software.construction.restaurantapp.service.OrderService;
import org.hse.software.construction.restaurantapp.utility.CartOrderConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartOrderConverter cartOrderConverter;

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order saveOrder(Order order) {
        log.info("[processPayment] notified that we save in DB Order :" + order);
        return orderRepository.save(order);
    }

    @Override
    public Order saveOrderFromCart(Cart cart) {
        log.info("[processPayment] notified that we DONT get Order :");
        Order order = cartOrderConverter.convert(cart);
        log.info("[processPayment] notified that we get Order :" + order);
        saveOrder(order);
        return order;


    }

    @Override
    public Order findById(UUID id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Order updateOrder(Order updatedOrder) {
        Optional<Order> existingOrderOptional = orderRepository.findById(updatedOrder.getId());

        if (existingOrderOptional.isPresent()) {
            Order existingOrder = existingOrderOptional.get();
            existingOrder.setTotalCost(updatedOrder.getTotalCost());
            existingOrder.setItems(updatedOrder.getItems());
            existingOrder.setOrderTime(updatedOrder.getOrderTime());
            return orderRepository.save(existingOrder);
        } else {
            throw new EntityNotFoundException("Order with ID " + updatedOrder.getId() + " not found.");
        }
    }

    @Override
    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }
}
