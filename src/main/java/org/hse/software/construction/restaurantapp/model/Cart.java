package org.hse.software.construction.restaurantapp.model;


import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    private UUID id = UUID.randomUUID();
    private double cost;

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 dish")
    @ElementCollection
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
    @Transient
    private LocalDateTime startTime;
    @Transient
    private Duration estimatedCookingTime;


}
