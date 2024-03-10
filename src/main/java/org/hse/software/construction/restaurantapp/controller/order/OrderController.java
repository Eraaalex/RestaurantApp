package org.hse.software.construction.restaurantapp.controller.order;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.model.Cart;
import org.hse.software.construction.restaurantapp.service.CartService;
import org.hse.software.construction.restaurantapp.service.DishService;
import org.hse.software.construction.restaurantapp.service.OrderService;
import org.hse.software.construction.restaurantapp.service.impl.CookingServiceImpl;
import org.hse.software.construction.restaurantapp.service.impl.order.BucketHandler;
import org.hse.software.construction.restaurantapp.utility.DishConverter;
import org.hse.software.construction.restaurantapp.utility.OrderCompletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private DishService dishService;
    private CartService cartService;
    private DishConverter dishConverter;
    private BucketHandler bucketHandler;
    private final CookingServiceImpl cookingService;
    private final OrderService orderService;

    @GetMapping("/details/{orderId}")
    public String showOrderForm(@PathVariable UUID orderId, Model model) {
        Cart cart = cartService.findById(orderId);
        model.addAttribute("dishes", dishService.findAllDish());
        model.addAttribute("order", cart);
        model.addAttribute("dishConverter", dishConverter);
        return "order-details";
    }


    @PostMapping("{orderId}/add-dish")
    public String addDishToOrder(@PathVariable UUID orderId, @RequestParam UUID dishId, @RequestParam int quantity) throws Exception {
        Cart currentCart = cartService.findById(orderId);
        if (bucketHandler.reserveDish(dishId, quantity)) {
            cookingService.addDishToOrder(orderId, dishId, quantity);
            return "redirect:/order/details/" + currentCart.getId();
        } else {
            log.info("Dish is not available");
            throw new Exception("Dish is not available");
        }
    }

    @GetMapping("details/canceled/{orderId}")
    public String showCanceledOrder(@PathVariable UUID orderId, Model model) {
        model.addAttribute("orderId", orderId.toString());
        return "canceled-order";
    }

    @GetMapping("details/paid/{orderId}")
    public String showPaidOrder(@PathVariable UUID orderId, Model model) {
        model.addAttribute("orderId", orderId.toString());
        return "paid-order";
    }

    @PostMapping("{orderId}/cancel-order")
    public String cancelOrder(@PathVariable UUID orderId) {
        log.info("Cancel order " + orderId);
        cookingService.cancelOrder(orderId);
        cartService.deleteBucket(orderId);
        return "redirect:/order/details/canceled/" + orderId;
    }

    @PostMapping("/{orderId}/pay")
    public String processPayment(@PathVariable UUID orderId) {
        Cart cart = cartService.findById(orderId);
        orderService.saveOrderFromCart(cart);
        return "redirect:/order/details/paid/" + orderId;
    }


    @EventListener
    public void onOrderCompleted(OrderCompletedEvent event) {
        processSavingOrder(event.getOrder());
    }

    private void processSavingOrder(Cart cart) {
        log.info("[process] notified that order Completed " + cart);
    }
}
