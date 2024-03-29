package org.hse.software.construction.restaurantapp.controller.dish;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.model.Human;
import org.hse.software.construction.restaurantapp.service.DishService;
import org.hse.software.construction.restaurantapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dishes")
@AllArgsConstructor
@Slf4j
public class DishController {
    DishService dishService;
    UserService userService;

    @GetMapping("/all-dishes")
    public List<Dish> findAllDishes() {
        return dishService.findAllDish();
    }


    @PostMapping("/add-dish")
    public String addDish(@ModelAttribute("dish") Dish dish, Errors errors) throws Exception {
        if (errors.hasErrors()) {
            throw new Exception("Error");
        }
        dishService.saveDish(dish);
        return "redirect:/";
    }

    @PostMapping("/update-dish")
    public String updateDish(@ModelAttribute("dish") Dish dish, @RequestParam("userName") String userName, BindingResult result, Model model) {
        log.info("UserId" + userName);
        Human currentUser = userService.findByName(userName);
        model.addAttribute("human", new Human());
        if (dishService.findByName(dish.getName()) == null) {
            result.rejectValue("name", "error.dish", "This dish name does not exist.");
        }
        if (result.hasErrors()) {
            log.info("UserId Err" + userName);
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("admin", currentUser);
            model.addAttribute("dish", dish);
            return "admin-main";
        }
        dishService.updateDish(dish);
        return "redirect:/users/admin/" + currentUser.getId();
    }

    @PostMapping("/delete-dish")
    public String deleteDish(@ModelAttribute("dish") Dish dish,
                             @RequestParam("userName") String userName,
                             BindingResult result, Model model) {
        Human currentUser = userService.findByName(userName);
        model.addAttribute("human", new Human());
        Dish currentDish = dishService.findByName(dish.getName());
        if (currentDish == null) {
            model.addAttribute("error", "This dish name does not exist.");
            model.addAttribute("admin", currentUser);
            return "admin-main";
        } else {
            log.info("Delete dish" + currentDish);
            dishService.deleteDish(currentDish.getId());
            return "redirect:/users/admin/" + currentUser.getId();
        }

    }
}
