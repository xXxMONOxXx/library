package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.PagesPath;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.entity.User;
import by.mishastoma.libraryweb.model.service.BookService;
import by.mishastoma.libraryweb.model.service.UserService;
import by.mishastoma.libraryweb.model.service.impl.BookServiceImpl;
import by.mishastoma.libraryweb.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;


public class GetUserInfoByIdCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = new Router();
        UserService userService = UserServiceImpl.getInstance();
        try {
            long id = Long.parseLong(request.getParameter(AttributeName.ID));
            Optional<User> optionalUser = userService.getUserById(id);
            if(optionalUser.isEmpty()){
                throw new CommandException("No user with id " + id);
            }
            BookService bookService = BookServiceImpl.getInstance();
            request.setAttribute(AttributeName.BOOKS_LIST, bookService.getBooksUserHas(id));
            request.setAttribute(AttributeName.USER, optionalUser.get());
            router.setPage(PagesPath.USER_PROFILE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
