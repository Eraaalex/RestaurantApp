package org.hse.software.construction.restaurantapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
//@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private UUID id = UUID.randomUUID();
    private double cost;

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 dish")
    @ElementCollection
//    @CollectionTable(name = "order_selected_dishes", joinColumns = @JoinColumn(name = "order_id"))
//    @MapKeyColumn(name = "dish_id")
//    @Column(name = "amount")
    private Map<UUID, Integer> selectedDishes = new HashMap<>();

    @Transient
    public void addDish(UUID dishId, int amount, double price) {
        if (selectedDishes.containsKey(dishId)) {
            int existingAmount = selectedDishes.get(dishId);
            selectedDishes.put(dishId, existingAmount + amount);
        } else {
            selectedDishes.put(dishId, amount);
        }
        cost += (price * amount);
    }

    @Transient
    public void removeDish(UUID dishId, double price) {
        if (selectedDishes.containsKey(dishId)) {
            int amount = selectedDishes.get(dishId);
            cost -= (price * amount);
            selectedDishes.remove(dishId);
        }
    }
    @Transient
    Status status = Status.ACCEPTED;

}