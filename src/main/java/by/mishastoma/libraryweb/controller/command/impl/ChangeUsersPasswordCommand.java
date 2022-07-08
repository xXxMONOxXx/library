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

public class ChangeUsersPasswordCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String oldPassword = request.getParameter(ParameterName.OLD_PASSWORD);
        String newPassword = request.getParameter(ParameterName.NEW_PASSWORD);
        if (oldPassword != null) {
            if (oldPassword.equals(newPassword)) {
                request.setAttribute(AttributeName.UPDATE_PASSWORD_FAILED, true);
            } else {
                long userId = Long.parseLong(request.getParameter(ParameterName.USER_ID));
                UserService userService = UserServiceImpl.getInstance();
                try {
                    if (userService.changeUsersPassword(userId, oldPassword, newPassword)) {
                        request.setAttribute(AttributeName.UPDATE_PASSWORD_SUCCESS, true);
                    } else {
                        request.setAttribute(AttributeName.UPDATE_PASSWORD_FAILED, true);
                    }
                    request.setAttribute(AttributeName.USER_ID, userId);
                } catch (ServiceException e) {
                    throw new CommandException(e);
                }
            }
        }
        else{
            request.setAttribute(AttributeName.UPDATE_PASSWORD_FAILED, true);
        }
        return new GetUserInfoByIdCommand().execute(request, response);
    }
}
