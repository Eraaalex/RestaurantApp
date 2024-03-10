package org.hse.software.construction.restaurantapp.controller.order;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.model.Cart;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.service.CartService;
import org.hse.software.construction.restaurantapp.service.DishService;
import org.hse.software.construction.restaurantapp.service.impl.CookingServiceImpl;
import org.hse.software.construction.restaurantapp.service.impl.order.BucketHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

        if (errors.hasErrors()) {
            return showDesignForm("Your order was not accepted");
        }
        double totalCost = bucketHandler.checkOrder(cart.getSelectedDishes());
        if (totalCost == 0.0) {

            return showDesignForm("Your order was not accepted");
        }
        cart.setCost(totalCost);

        Cart savedCart = cartService.saveBucket(cart);
        cookingService.processOrder(cart);
        return new ModelAndView("redirect:/order/details/" + savedCart.getId());
    }
}
