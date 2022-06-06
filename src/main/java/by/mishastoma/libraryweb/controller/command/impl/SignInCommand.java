package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.*;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.model.entity.User;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.service.UserService;
import by.mishastoma.libraryweb.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class SignInCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        Router router = new Router();
        HttpSession session = request.getSession();
        String login = request.getParameter(ParameterName.LOGIN);
        String password = request.getParameter(ParameterName.PASSWORD);
        try {
            Optional<User> optionalUser = userService.signIn(login, password);
            if(optionalUser.isPresent()){
                // todo session create
                router.setPage(PagesPath.HOME);
            }
            else{
                request.setAttribute(AttributeName.SIGN_IN_MESSAGE, Messages.INCORRECT_LOGIN_OR_PASSWORD);
                router.setPage(PagesPath.ENTRY_SIGN_IN);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
