package ru.java.mentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.java.mentor.model.User;
import ru.java.mentor.service.UserServiceInterface;

@Controller
@PropertySource(value = "classpath:config.properties")
public class RegistrationController {
    private UserServiceInterface service;
    private Environment environment;

    @Autowired
    public void setUserService(UserServiceInterface service) {
        this.service = service;
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/reg")
    public String reg(@ModelAttribute("message") String message) {
        return "registration";
    }

    @PostMapping("/reg/user")
    public String regUser(@ModelAttribute("user")User user, Model model) {

        if (service.addUser(user)) {
            return "redirect:/";
        } else {
            model.addAttribute("message", environment.getRequiredProperty("invalidData"));
        }
        return "redirect:/reg";
    }
}
