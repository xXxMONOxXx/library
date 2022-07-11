package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.PagesPath;
import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.entity.UserRole;
import by.mishastoma.libraryweb.model.service.UserService;
import by.mishastoma.libraryweb.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddBalanceToUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        long userIdEntering = (long) session.getAttribute(AttributeName.USER_ID);
        String role = (String) session.getAttribute(AttributeName.ROLE);
        long userId = Long.parseLong(request.getParameter(ParameterName.USER_ID));
        if (userIdEntering != userId && !role.equals(UserRole.ADMIN.toString())) {
            return new Router(PagesPath.PERMISSION_DENIED);
        }
        try {
            String changeBalance = request.getParameter(ParameterName.CHANGE_BALANCE);
            if (userService.addToUsersBalance(userId, changeBalance)) {
                request.setAttribute(AttributeName.BALANCE_CHANGE_SUCCESS, true);
                logger.info("Changed users balance, id - {}", userId);
            } else {
                request.setAttribute(AttributeName.BALANCE_CHANGE_FAILED, true);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new GetUserInfoByIdCommand().execute(request, response);
    }
}
