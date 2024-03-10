package org.hse.software.construction.restaurantapp.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.model.Cart;
import org.hse.software.construction.restaurantapp.service.impl.CookingServiceImpl;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.service.impl.order.BucketHandler;
import org.hse.software.construction.restaurantapp.service.DishService;
import org.hse.software.construction.restaurantapp.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/design")
@AllArgsConstructor
public class CartController {
    private final DishService dishService;
    private final CartService cartService;
    private final BucketHandler bucketHandler;
    private final CookingServiceImpl cookingService;

    @ModelAttribute(name = "order")
    public Cart order() {
        return new Cart();
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
    public ModelAndView processCart(@ModelAttribute(name = "order") Cart cart, Errors errors) {
        log.info("[processPreOrder]" + cart);
        if (errors.hasErrors()) {
            log.info("[processPreOrder] ERRORR" + cart);
            return showDesignForm("Your order was not accepted");
        }
        double totalCost = bucketHandler.checkOrder(cart.getSelectedDishes());
        if (totalCost == 0.0) {
            log.info("[processPreOrder] ERRORR total cost " + cart);
            return showDesignForm("Your order was not accepted");
        }
        cart.setCost(totalCost);
        log.info("[processPreOrder] 2" + cart);
        Cart savedCart = cartService.saveBucket(cart);
        cookingService.processOrder(cart);
        return new ModelAndView("redirect:/order/details/" + savedCart.getId());
    }

    @PostMapping("/save-order")
    public ModelAndView saveOrder(@ModelAttribute("order") Cart cart) {
        cartService.saveBucket(cart);
        return new ModelAndView("redirect:/menu");
    }
}
