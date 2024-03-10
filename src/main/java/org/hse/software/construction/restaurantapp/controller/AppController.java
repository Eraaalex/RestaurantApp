package org.hse.software.construction.restaurantapp.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.model.Cart;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.model.Human;
import org.hse.software.construction.restaurantapp.service.DishService;
import org.hse.software.construction.restaurantapp.service.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("")
@AllArgsConstructor
public class AppController {
    private final DishService dishService;
    private final UserService service;

    @GetMapping("/")
    public ModelAndView menu(Model model) {
        log.info("[menu]" + dishService.findAllDish());
        model.addAttribute("dishes", dishService.findAllDish());
        return new ModelAndView("menu");
    }

    @GetMapping("/error")
    public ModelAndView showErrorForm(String message) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("message", message);
        return model;
    }

    @GetMapping("/all-users")
    public List<Human> getAllUsers() {
        return service.findAllUsers();
    }

    @GetMapping("/{name}")
    public Dish findByName(@PathVariable String name) {
        return dishService.findByName(name);
    }

    @PutMapping("/update-dish")
    public Dish updateDish(@RequestBody Dish dish) {
        return dishService.updateDish(dish);
    }

    @DeleteMapping("/delete_dish/{name}")
    public void deleteDish(@PathVariable String name) {
        dishService.deleteDishByName(name);
    }


    @PostMapping("/new-user")
    public String addUser(@RequestBody Human user) {
        service.addUser(user, true);
        return "User is saved";
    }


    @ModelAttribute(name = "order")
    public Cart order() {
        return new Cart();
    }

    @ModelAttribute(name = "dish")
    public Dish dish() {
        return new Dish();
    }

}
