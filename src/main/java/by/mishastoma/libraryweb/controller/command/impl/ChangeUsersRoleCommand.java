package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.service.UserService;
import by.mishastoma.libraryweb.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeUsersRoleCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long userId = Long.parseLong(request.getParameter(ParameterName.USER_ID));
        String role = request.getParameter(ParameterName.USER_ROLE);
        UserService userService = UserServiceImpl.getInstance();
        try {
            if (!userService.changeUsersRole(userId, role)) {
                request.setAttribute(AttributeName.FAILED_TO_CHANGE_USERS_ROLE, true);
            } else {
                request.setAttribute(AttributeName.CHANGE_USERS_ROLE_SUCCESS, true);
                logger.info("Changed users role, user id - {}, new role - {}", userId, role);
            }
            return new GetUserInfoByIdCommand().execute(request, response);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
