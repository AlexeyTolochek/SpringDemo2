package ru.java.mentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.java.mentor.model.User;
import ru.java.mentor.service.UserService;

@Controller
@PropertySource(value = "classpath:config.properties")
public class RegistrationController {
    private UserService service;
    private Environment environment;

    @Autowired
    public void setUserService(UserService userService) {
        this.service = userService;
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @GetMapping(value = "/reg")
    public ModelAndView addPage(@ModelAttribute("message") String message) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("user")User user) {
        ModelAndView modelAndView = new ModelAndView();

        if (notNullData(user)) {
            if (service.isExistLogin(user.getLogin())) {
                modelAndView.addObject("message", environment.getRequiredProperty("falseAdd"));
                modelAndView.setViewName("redirect:/reg");
            } else {
                service.addUser(user);
                modelAndView.setViewName("redirect:/");
            }
        } else {
            modelAndView.addObject("message", environment.getRequiredProperty("invalidData"));
            modelAndView.setViewName("redirect:/reg");
        }

        return modelAndView;
    }


    private boolean notNullData(User user) {
        return user.getName() != null && !user.getName().isEmpty() &&
                user.getLogin() != null && !user.getLogin().isEmpty() &&
                user.getPassword() != null && !user.getPassword().isEmpty() &&
                user.getAddress() != null && !user.getAddress().isEmpty();
    }
}
