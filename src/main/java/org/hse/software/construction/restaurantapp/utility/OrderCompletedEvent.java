package org.hse.software.construction.restaurantapp.utility;

import org.hse.software.construction.restaurantapp.model.Cart;
import org.springframework.context.ApplicationEvent;

public class OrderCompletedEvent extends ApplicationEvent {
    private final Cart order;

    public OrderCompletedEvent(Object source, Cart order) {
        super(source);
        this.order = order;
    }

    public Cart getOrder() {
        return order;
    }
}