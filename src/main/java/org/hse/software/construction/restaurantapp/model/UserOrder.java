package org.hse.software.construction.restaurantapp.model;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

//public class UserOrder {
//
//    @Id
//    private UUID id = UUID.randomUUID();
//    private double cost;
//
//    @NotNull
//    @Size(min = 1, message = "You must choose at least 1 dish")
//    @ElementCollection
//    private Map<UUID, Integer> selectedDishes = new HashMap<>();
//
//
//    @ManyToMany
//    @JoinTable(
//            name = "order_dish",
//            joinColumns = @JoinColumn(name = "order_id"),
//            inverseJoinColumns = @JoinColumn(name = "dish_id"))
//    private Set<Dish> dishes = new HashSet<>();
//
//
//    @Transient
//    public void addDish(UUID dishId, int amount, double price) {
//        if (selectedDishes.containsKey(dishId)) {
//            int existingAmount = selectedDishes.get(dishId);
//            selectedDishes.put(dishId, existingAmount + amount);
//        } else {
//            selectedDishes.put(dishId, amount);
//        }
//        cost += (price * amount);
//    }
//
//    @Transient
//    public void removeDish(UUID dishId, double price) {
//        if (selectedDishes.containsKey(dishId)) {
//            int amount = selectedDishes.get(dishId);
//            cost -= (price * amount);
//            selectedDishes.remove(dishId);
//        }
//    }
//    @Transient
//    Status status = Status.ACCEPTED;
//
//}
