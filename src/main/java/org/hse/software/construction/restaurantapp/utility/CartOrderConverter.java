package org.hse.software.construction.restaurantapp.utility;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.model.Cart;
import org.hse.software.construction.restaurantapp.model.Order;
import org.hse.software.construction.restaurantapp.model.OrderDish;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;


@AllArgsConstructor
@Component
public class CartOrderConverter implements Converter<Cart, Order> {

    @Override
    public Order convert(Cart cart) {
        Order order = new Order();

        for (Map.Entry<UUID, Integer> entry : cart.getSelectedDishes().entrySet()) {
            OrderDish item = OrderDish
                    .builder()
                    .dishId(entry.getKey())
                    .quantity(entry.getValue())
                    .build();

            order.addDish(item);

        }
        order.setTotalCost(cart.getCost());

        return order;
    }

}