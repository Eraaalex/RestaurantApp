package org.hse.software.construction.restaurantapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private double totalCost;
    private LocalDateTime orderTime = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDish> items = new ArrayList<>();

    public void addDish(OrderDish item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        item.setOrder(this);
        this.items.add(item);
    }
}
