package org.hse.software.construction.restaurantapp.utility;

import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.model.Cart;
import org.hse.software.construction.restaurantapp.model.Order;
import org.hse.software.construction.restaurantapp.model.OrderDish;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;


@Service
@Slf4j
public class CartOrderConverter {
    public Order convert(Cart cart) {
        Order order = new Order();

        for (Map.Entry<UUID, Integer> entry : cart.getSelectedDishes().entrySet()) {
            OrderDish item = new OrderDish();

            item.setDishId(entry.getKey());

            item.setQuantity(entry.getValue());
            order.addDish(item);

        }
        order.setTotalCost(cart.getCost());

        return order;
    }

}