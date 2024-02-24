package org.hse.software.construction.restaurantapp.controller;


import lombok.AllArgsConstructor;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.model.Human;
import org.hse.software.construction.restaurantapp.model.Order;
import org.hse.software.construction.restaurantapp.service.DishService;
import org.hse.software.construction.restaurantapp.service.OrderService;
import org.hse.software.construction.restaurantapp.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class DishController {
    private final DishService dishService;
    private final OrderService orderService;
    @GetMapping("/menu")
    public ModelAndView menu(Model model) {
        model.addAttribute("products", dishService.findAllDish());
        return new ModelAndView("menu");
    }


    @PostMapping("/save-order")
    public ModelAndView saveOrder(@ModelAttribute("order") Order order) {
        orderService.saveOrder(order);
        return new ModelAndView("redirect:/api/v1/menu");
    }


    @GetMapping("/all-dishes")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Dish> findAllDishes() {
        return dishService.findAllDish();
    }


    @PostMapping("/new-dish")//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView saveDish(@RequestBody Dish dish) {
        dishService.saveDish(dish);
        return new ModelAndView("redirect:/menu");
    }


    @GetMapping("/{name}")
    public Dish findByName(@PathVariable String name) {
        return dishService.findByName(name);
    }

    @PutMapping("/update-dish")//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Dish updateDish(@RequestBody Dish dish) {
        return dishService.updateDish(dish);
    }

    @DeleteMapping("/delete_dish/{name}")//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteDish(@PathVariable String name) {
        dishService.deleteDish(name);
    }

    private final UserService service;

    @PostMapping("/new-user")
    public String addUser(@RequestBody Human user) {
        service.addUser(user);
        return "User is saved";
    }


    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "dish")
    public Dish dish() {
        return new Dish();
    }

}
