package ru.java.mentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.java.mentor.model.User;
import ru.java.mentor.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@PropertySource(value = "classpath:config.properties")
public class AdminController {
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

    @GetMapping(value = "/admin")
    public ModelAndView login(@ModelAttribute("message") String message, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user!=null) {
            if (user.getRole().equals("Administrator")) {
                String idStr = request.getParameter("id");
                String edit = request.getParameter("edit");
                String delete = request.getParameter("delete");
                Long id;

                if (delete != null) {
                    id = Long.parseLong(idStr);
                    User userDelete = service.getUserById(id);
                    if (userDelete != null) {
                        service.deleteUser(userDelete);
                    }
                }

                if (edit != null) {
                    id = Long.parseLong(idStr);
                    User userEdit = service.getUserById(id);
                    request.setAttribute("userEdit", userEdit);
                }

                List<User> list = service.getAllUsers();
                request.setAttribute("list", list);


                request.setAttribute("nameUser", user.getName());
                modelAndView.setViewName("admin");
            } else {
                request.setAttribute("nameUser", user.getName());
                modelAndView.addObject("message", environment.getRequiredProperty("onlyAdmin"));
                modelAndView.setViewName("redirect:/user");
            }
        } else {
            modelAndView.addObject("message", environment.getRequiredProperty("invalidPass"));
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public ModelAndView authUser(@ModelAttribute("user") User user, HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView();
        final String idStr = req.getParameter("id");
        final String name = req.getParameter("name");
        final String login = req.getParameter("login");
        final String password = req.getParameter("password");
        final String address = req.getParameter("address");
        final String role = req.getParameter("role");
        final String action = req.getParameter("action");

        if (requestIsValid(name, login, password, address)) {
            final User userEdit = new User(name, login, password, address, role);

            if (action != null) {
                if (action.equalsIgnoreCase("add")) {
                    service.addUser(userEdit);
                }
                if (action.equalsIgnoreCase("edit")) {
                    userEdit.setId(Long.parseLong(idStr));
                    service.editUser(userEdit);
                }
            }
        }
        modelAndView.setViewName("redirect:/admin");
        return modelAndView;
    }

    private boolean requestIsValid(String name, String login, String password, String address) {
        return name != null && !name.isEmpty() &&
                login != null && !login.isEmpty() &&
                password != null && !password.isEmpty() &&
                address != null && !address.isEmpty();
    }
}
