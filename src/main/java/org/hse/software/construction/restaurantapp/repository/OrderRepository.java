package org.hse.software.construction.restaurantapp.repository;

import org.hse.software.construction.restaurantapp.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Cart, UUID> {
}
