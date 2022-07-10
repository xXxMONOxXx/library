package by.mishastoma.libraryweb.controller.filter;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.controller.command.CommandType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "CommandFilter", urlPatterns = "/controller")
public class CommandFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession();
        String userRole = (String) session.getAttribute(AttributeName.ROLE);
        String commandStr = httpRequest.getParameter(ParameterName.COMMAND);
        if (commandStr == null) {
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        CommandType command = CommandType.valueOf(commandStr.toUpperCase());
        boolean isAllowed;
        if (userRole == null) {
            isAllowed = UserRolePermission.GUEST.isAllowed(command);
        } else {
            switch (userRole) {
                case "ADMIN" -> isAllowed = UserRolePermission.ADMIN.isAllowed(command);

                case "LIBRARIAN" -> isAllowed = UserRolePermission.LIBRARIAN.isAllowed(command);

                case "USER" -> isAllowed = UserRolePermission.USER.isAllowed(command);

                default -> {
                    httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            }
        }
        if (isAllowed) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
