package org.hse.software.construction.restaurantapp.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.software.construction.restaurantapp.model.Human;
import org.hse.software.construction.restaurantapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
        Human existing = userService.findByName(human.getName());
        if (existing != null) {
            return "login";
        }
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid user data");
        }

        userService.addUser(human);
        log.info("User registered: " + human.getName());
        return "redirect:/login";
    }






}