package org.hse.software.construction.restaurantapp.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "order_dish")
public class OrderDish {
    @Id
    @GeneratedValue
    private UUID id;
    private UUID dishId;
    private int quantity;

    @ManyToOne
    private Order order;

}
