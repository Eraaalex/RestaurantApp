package org.hse.software.construction.restaurantapp.controller.authentification;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.model.Human;
import org.hse.software.construction.restaurantapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
class UserController {

    UserService userService;

    @GetMapping("/admin/{adminId}")
    public String showAdminForm(@PathVariable UUID adminId, Model model) throws Exception {
        Human user = userService.findById(adminId);
        if (user == null) {
            throw new Exception("User not found");
        }
        model.addAttribute("admin", user);
        model.addAttribute("adminId", user.getId().toString()); // thymeleaf can't parse UUID
        model.addAttribute("dish", createDishModel());
        model.addAttribute("human", new Human());
        return "admin-main";
    }


    @GetMapping()
    public String users(Model model) {
        List<Human> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @ModelAttribute("dish")
    public Dish createDishModel() {
        return new Dish();
    }

}