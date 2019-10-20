package ru.java.mentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.java.mentor.model.User;
import ru.java.mentor.service.UserServiceInterface;
import javax.servlet.http.HttpServletRequest;

@Controller
@PropertySource(value = "classpath:config.properties")
public class AuthorizationController {
    private UserServiceInterface service;
    private Environment environment;

    @Autowired
    public void setService(UserServiceInterface service) {
        this.service = service;
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @GetMapping(value = "/")
    public String auth(@ModelAttribute("message") String message) {
        return "index";
    }

    @PostMapping(value = "/auth")
    public String authUser(@ModelAttribute("user") User user, Model model, HttpServletRequest request) {

       if (service.validateUser(user)) {
            user = service.getUserByLogin(user.getLogin());
            request.getSession().setAttribute("user", user);
           return "redirect:/user";
        } else {
            model.addAttribute("message", environment.getRequiredProperty("invalidData"));
        }
        return"redirect:/";
    }
}
