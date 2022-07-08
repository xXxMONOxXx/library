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

public class UnblockUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long userId = Long.parseLong(request.getParameter(ParameterName.USER_ID));
        UserService userService = UserServiceImpl.getInstance();
        try {
            if(userService.setIsBlockState(userId, false)){
                request.setAttribute(AttributeName.BLOCKED_OR_UNBLOCKED_USER_SUCCESS, true);
            }
            else{
                request.setAttribute(AttributeName.BLOCKED_OR_UNBLOCKED_USER_FAILED, true);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new GetAllUsersCommand().execute(request, response);
    }
}
