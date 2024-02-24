package org.hse.software.construction.restaurantapp.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank(message = "Name is required")
    private String name;
    @Digits(message = "Price is required", integer = 6, fraction = 2)
    private double price;
    private int amount;
    @Version
    private Long version;

}
