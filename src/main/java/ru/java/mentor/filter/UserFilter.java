package ru.java.mentor.filter;

import org.springframework.context.annotation.PropertySource;
import ru.java.mentor.model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "UserFilter", urlPatterns = {"/user"})
@PropertySource(value = "classpath:config.properties")
public class UserFilter implements Filter {

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
            chain.doFilter(request, response);
        }

        response.sendRedirect(path);
    }

    @Override
    public void destroy() {
    }
}

