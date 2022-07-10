package by.mishastoma.libraryweb.controller.filter;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.PagesPath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "AuthorizedFilter", urlPatterns = {"/pages/admin/*", "/pages/librarian/*", "/pages/user/*"})
public class AuthorizedFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession();
        String userRole = (String) session.getAttribute(AttributeName.ROLE);
        if (userRole == null) {
            httpResponse.sendRedirect(PagesPath.ENTRY_SIGN_IN);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
