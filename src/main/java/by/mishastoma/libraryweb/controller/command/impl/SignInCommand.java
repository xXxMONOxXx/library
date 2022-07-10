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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class SignInCommand implements Command {

    //todo check for minus balance -> block

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        Router router = new Router();
        String login = request.getParameter(ParameterName.LOGIN);
        String password = request.getParameter(ParameterName.PASSWORD);
        try {
            Optional<User> user = userService.signIn(login, password);
            if(user.isPresent()){
                logger.info("User - {}, sign in.", user.get().getId());
                if(user.get().isBlocked()){
                    return new Router(PagesPath.BLOCKED_USER);
                }
                HttpSession session = request.getSession();
                session.setAttribute(AttributeName.USER_ID, user.get().getId());
                session.setAttribute(AttributeName.ROLE, user.get().getRole().toString());
                return new GoToAllBooksPageCommand().execute(request, response);
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
