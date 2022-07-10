package by.mishastoma.libraryweb.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter
public class BaseFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession();
        httpResponse.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
        httpResponse.setHeader("Pragma","no-cache");
        httpResponse.setDateHeader ("Expires", 0);
    }
}
