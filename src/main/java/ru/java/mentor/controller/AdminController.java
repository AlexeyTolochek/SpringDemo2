package ru.java.mentor.controller;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.core.env.Environment;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.*;
        import ru.java.mentor.model.User;
        import ru.java.mentor.service.UserServiceInterface;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpSession;
        import java.util.List;

@Controller
public class AdminController {
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

    @GetMapping("/admin")
    public String userList(@ModelAttribute("edit") String edit, @ModelAttribute("user") User user,
                           @ModelAttribute("message") String message, Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute("user");
        User editUser = service.editUserView(edit, user);
        List<User> list = service.getAllUsers();
        model.addAttribute("list", list);
        model.addAttribute("userEdit", editUser);
        model.addAttribute("nameUser", admin.getName());
        return "admin";
    }

    @PostMapping(value = "/admin/add")
    public String userAdd(@ModelAttribute("user") User user, Model model) {

        if (!service.addUser(user)) {
            model.addAttribute("message", environment.getRequiredProperty("invalidData"));
        }
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/update")
    public String userUpdate(@ModelAttribute("user") User user, Model model) {

        if (!service.updateUser(user)) {
            model.addAttribute("message", environment.getRequiredProperty("invalidData"));
        }
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/delete")
    public String userDelete(@ModelAttribute("user") User user) {

        service.deleteUser(user);
        return "redirect:/admin";
    }
}
