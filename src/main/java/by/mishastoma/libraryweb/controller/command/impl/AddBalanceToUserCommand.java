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

public class AddBalanceToUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        try {
            long userId = Long.parseLong(request.getParameter(ParameterName.USER_ID));
            String changeBalance = request.getParameter(ParameterName.CHANGE_BALANCE);
            request.setAttribute(AttributeName.BALANCE_CHANGE_SUCCESS,
                    userService.addToUsersBalance(userId, changeBalance));
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new GetUserInfoByIdCommand().execute(request, response);
    }
}
