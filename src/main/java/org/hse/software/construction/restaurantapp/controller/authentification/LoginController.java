package org.hse.software.construction.restaurantapp.controller.authentification;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.model.Dish;
import org.hse.software.construction.restaurantapp.model.Human;
import org.hse.software.construction.restaurantapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping()
@Slf4j
public class LoginController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("human", new Human());
        return "login";
    }

    @GetMapping("/signup")
    public String registration(Model model) {
        model.addAttribute("human", new Human());
        return "login";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("human") Human human, BindingResult bindingResult, Model model) {
        log.info("User registered: 1 " + human.getName());
        Human existing = userService.findByName(human.getName());
        log.info("User registered: 2 " + human.getName());
        if (existing != null) {
            return "login";
        }
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid user data");
        }

        userService.addUser(human, false);
        log.info("User registered: " + human.getName());
        return "redirect:/login";
    }


    @PostMapping("/signup/admin/")
    public String signupAdmin(@ModelAttribute("human") Human human,
                              @RequestParam("userName") String userName,
                              BindingResult result, Model model) {
        Human existing = userService.findByName(human.getName());
        Human currentUser = userService.findByName(userName);
        if (existing != null) {
            result.rejectValue("userEr", "error.user",
                    "User with this name has already exist.");
            return "redirect:/users/admin/" + currentUser.getId();
        }
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("admin", currentUser);
            model.addAttribute("dish", new Dish());
            return "admin-main";
        }

        userService.addUser(human, true);
        return "redirect:/users/admin/" + currentUser.getId();
    }


}