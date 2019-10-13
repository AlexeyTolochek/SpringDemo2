package ru.java.mentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.java.mentor.model.User;
import ru.java.mentor.service.UserService;
import javax.servlet.http.HttpServletRequest;

@Controller
@PropertySource(value = "classpath:config.properties")
public class MainController {
    private UserService service;
    private Environment environment;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @GetMapping(value = "/")
    public ModelAndView login(@ModelAttribute("message") String message) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView authUser(@ModelAttribute("user") User user, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        if (notNullData(user)) {
            if (service.validateUser(user.getLogin(), user.getPassword())) {
                user = service.getUserByLogin(user.getLogin());
                request.getSession().setAttribute("user", user);
                modelAndView.setViewName("redirect:/user");
            } else {
                modelAndView.addObject("message", environment.getRequiredProperty("invalidPass"));
                modelAndView.setViewName("redirect:/");
            }
        } else {
            modelAndView.addObject("message", environment.getRequiredProperty("invalidData"));
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    private boolean notNullData(User user) {
        return user.getLogin() != null && !user.getLogin().isEmpty() &&
                user.getPassword() != null && !user.getPassword().isEmpty();
    }
}
