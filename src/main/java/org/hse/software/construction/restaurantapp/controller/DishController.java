package org.hse.software.construction.restaurantapp.controller;


import lombok.AllArgsConstructor;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.service.DishService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/dishes")
@AllArgsConstructor
public class DishController {
    DishService dishService;
    @GetMapping("/all-dishes")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Dish> findAllDishes() {
        return dishService.findAllDish();
    }

    @PostMapping("/new-dish")//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView saveDish(@RequestBody Dish dish) {
        dishService.saveDish(dish);
        return new ModelAndView("redirect:/menu");
    }

    @PostMapping("/add-dish")
    public ModelAndView addDish(@Valid Dish dish, Errors errors){

        if (errors.hasErrors()) {
//            return showErrorForm("Your dish changes was not accepted");

        }
        dishService.saveDish(dish);
        return new ModelAndView("redirect:/menu");
    }

}
