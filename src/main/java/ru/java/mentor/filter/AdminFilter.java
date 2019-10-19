package ru.java.mentor.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ru.java.mentor.model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import javax.servlet.Filter;

@WebFilter(filterName = "AdminFilter", urlPatterns = {"/admin"})
@PropertySource(value = "classpath:config.properties")
public class AdminFilter implements Filter {
    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String path = request.getContextPath() + "/";

        User user = (User) session.getAttribute("user");

        if (user!=null) {
            if (user.getRole().equals("Administrator")) {
                chain.doFilter(request, response);
            } else if (user.getRole().equals("User")) {
                path = request.getContextPath() + "/user/admin";
                response.sendRedirect(path);
            }
        } else {
            response.sendRedirect(path);
        }
    }

    @Override
    public void destroy() {
    }
}

