package org.hse.software.construction.restaurantapp.model;

import java.util.Map;
import java.util.UUID;

public class CartOrderConverter {
    public static Order convert(Cart cart) {
        Order order = new Order();
        for (Map.Entry<UUID, Integer> entry : cart.getSelectedDishes().entrySet()) {
            OrderDish item = new OrderDish();
            item.setId(entry.getKey());
            item.setQuantity(entry.getValue());
            order.addDish(item);
        }
        return order;
    }

    private static double calculateCost(UUID productId, Integer quantity) {
        // Implementation depends on how you determine the cost of each item
        return 0; // Placeholder return value
    }
}