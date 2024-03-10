package org.hse.software.construction.restaurantapp.service;

import org.hse.software.construction.restaurantapp.model.Cart;

import java.util.List;
import java.util.UUID;

public interface CartService {

    List<Cart> findAllBuckets();

    Cart saveBucket(Cart cart);

    Cart findById(UUID id);

    Cart updateBucket(Cart cart);

    void deleteBucket(UUID id);

}
