package org.hse.software.construction.restaurantapp.controller;


import lombok.AllArgsConstructor;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.model.Human;
import org.hse.software.construction.restaurantapp.service.DishService;
import org.hse.software.construction.restaurantapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/dishes")
@AllArgsConstructor
public class DishController {
    private final DishService dishService;


    @GetMapping("/welcome")
    public String welcome() {
        return "WELCOME!";
    }

    @GetMapping("/all-dishes")
    public List<Dish> findAllDishes() {
        return dishService.findAllDish();
    }


    @PostMapping("/save-dish")//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Dish saveDish(@RequestBody Dish dish) {
        return dishService.saveDish(dish);
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

}
