package org.hse.software.construction.restaurantapp.service.impl.order;

import lombok.AllArgsConstructor;
import org.hse.software.construction.restaurantapp.model.Cart;
import org.hse.software.construction.restaurantapp.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    List<Cart> buckets;

    @Override
    public List<Cart> findAllBuckets() {
        return buckets;
    }

    @Override
    public Cart saveBucket(Cart cart) {

        buckets.add(cart);
        return cart;
    }

    @Override
    public Cart findById(UUID id) {
        return buckets.stream().filter(order -> order.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Cart updateBucket(Cart cart) {
        buckets.removeIf(bucket -> bucket.getId().equals(cart.getId()));
        buckets.add(cart);
        return cart;
    }

    @Override
    public void deleteBucket(UUID id) {
        buckets.removeIf(order -> order.getId().equals(id));
    }


}
