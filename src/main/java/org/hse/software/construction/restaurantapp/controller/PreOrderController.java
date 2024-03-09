package org.hse.software.construction.restaurantapp.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.CookingService;
import org.hse.software.construction.restaurantapp.model.Order;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.service.OrderHandler;
import org.hse.software.construction.restaurantapp.service.DishService;
import org.hse.software.construction.restaurantapp.service.BucketService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/design")
@AllArgsConstructor
public class PreOrderController {
    private final DishService dishService;
    private final BucketService bucketService;
    private final OrderHandler orderHandler;
    private final CookingService cookingService;

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "dish")
    public Dish dish() {
        return new Dish();
    }

    @GetMapping
    public ModelAndView showDesignForm(String message) {

        List<Dish> dishes = dishService.findAllDish();
        ModelAndView modelAndView = new ModelAndView("design");
        log.error("[showDesignForm]" + dishes);
        modelAndView.addObject("dishes", dishes);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView processPreOrder(@ModelAttribute Order order, Errors errors) {
        log.info("[processPreOrder]" + order);
        if (errors.hasErrors()) {
            return showDesignForm("Your order was not accepted");
        }
        double totalCost = orderHandler.checkOrder(order.getSelectedDishes());
        if (totalCost == 0.0) {
            return showDesignForm("Your order was not accepted");
        }
        order.setCost(totalCost);
        log.info("[processPreOrder] 2" + order);
        Order savedOrder = bucketService.saveOrder(order);
        cookingService.processOrder(order);
        return new ModelAndView("redirect:/order/details/" + savedOrder.getId());
    }

    @PostMapping("/save-order")
    public ModelAndView saveOrder(@ModelAttribute("order") Order order) {
        bucketService.saveOrder(order);
        return new ModelAndView("redirect:/menu");
    }
}
