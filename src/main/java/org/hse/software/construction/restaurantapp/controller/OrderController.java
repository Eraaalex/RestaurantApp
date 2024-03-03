package org.hse.software.construction.restaurantapp.controller;


import jakarta.persistence.OptimisticLockException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.CookingService;
import org.hse.software.construction.restaurantapp.DishConverter;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.model.Order;
import org.hse.software.construction.restaurantapp.model.Status;
import org.hse.software.construction.restaurantapp.service.DishService;
import org.hse.software.construction.restaurantapp.service.OrderHandler;
import org.hse.software.construction.restaurantapp.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    private OrderService orderService;
    private DishConverter dishConverter;
    private OrderHandler orderHandler;
    private final CookingService cookingService;

    @GetMapping("/details/{orderId}")
    public ModelAndView showOrderForm(@PathVariable UUID orderId, Model model) {
        Order order = orderService.findById(orderId);
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
    public String addDishToOrder(@PathVariable UUID orderId, @RequestParam UUID dishId, @RequestParam int quantity) {
        Order currentOrder = orderService.findById(orderId);
        Dish dish = dishService.findById(dishId);

        if (orderHandler.checkToAddDish(dishId, quantity)) {
            cookingService.addDishToOrder(orderId, dishId, quantity);
            return "redirect:/order/details/" + currentOrder.getId();
        } else {
            log.info("Dish is not available");
            return "redirect:/errorPage"; // Redirect to an error page or handle the error appropriately
        }
    }



}
