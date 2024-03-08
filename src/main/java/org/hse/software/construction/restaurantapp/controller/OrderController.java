package org.hse.software.construction.restaurantapp.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.CookingService;
import org.hse.software.construction.restaurantapp.DishConverter;
import org.hse.software.construction.restaurantapp.model.Order;
import org.hse.software.construction.restaurantapp.service.DishService;
import org.hse.software.construction.restaurantapp.service.OrderHandler;
import org.hse.software.construction.restaurantapp.service.BucketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private DishService dishService;
    private BucketService bucketService;
    private DishConverter dishConverter;
    private OrderHandler orderHandler;
    private final CookingService cookingService;

    @GetMapping("/details/{orderId}")
    public ModelAndView showOrderForm(@PathVariable UUID orderId, Model model) {
        Order order = bucketService.findById(orderId);
        model.addAttribute("dishes", dishService.findAllDish());
        model.addAttribute("order", order);
        model.addAttribute("dishConverter", dishConverter);

        return new ModelAndView("order-details");
    }

    @PostMapping
    public ModelAndView processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return new ModelAndView("design");
        }
        sessionStatus.setComplete();
        return new ModelAndView("redirect:/order");
    }
    @PostMapping("{orderId}/add-dish")
    public String addDishToOrder(@PathVariable UUID orderId, @RequestParam UUID dishId, @RequestParam int quantity) throws Exception {
        Order currentOrder = bucketService.findById(orderId);
        if (orderHandler.reserveDish(dishId, quantity)) {
            cookingService.addDishToOrder(orderId, dishId, quantity);
            return "redirect:/order/details/" + currentOrder.getId();
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
    @PostMapping("{orderId}/cancel-order")
    public String cancelOrder(@PathVariable UUID orderId) {
        log.info("Cancel order " + orderId);
        cookingService.cancelOrder(orderId);
        Order currentOrder = bucketService.findById(orderId);
        return "redirect:/order/details/canceled/" + currentOrder.getId();
    }
}
