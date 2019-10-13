package ru.java.mentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import ru.java.mentor.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@PropertySource(value = "classpath:config.properties")
public class UserController {
    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @GetMapping(value = "/user")
    public ModelAndView login(@ModelAttribute("message") String message, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user!=null) {
            request.setAttribute("nameUser", user.getName());
            modelAndView.setViewName("userPage");
        } else {
            modelAndView.addObject("message", environment.getRequiredProperty("invalidPass"));
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }
}
