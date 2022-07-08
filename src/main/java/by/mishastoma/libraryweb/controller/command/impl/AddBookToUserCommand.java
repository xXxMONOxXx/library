package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.PagesPath;
import by.mishastoma.libraryweb.controller.ParameterName;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class AddBookToUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    private static final int MAX_NUMBER_OF_USERS_BOOKS = 10;

    private static final int BOOK_PRICE = 3;

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long userId = Long.parseLong(request.getParameter(ParameterName.USER_ID));
        if (userIsBlocked(userId)) {
            return new Router(PagesPath.BLOCKED_USER);
        }
        long bookId = Long.parseLong(request.getParameter(ParameterName.BOOK_ID));
        if(!numberOfBooksUserHasWithinALimit(userId)){
            request.setAttribute(AttributeName.GOT_BOOK_FAILED, true);
        }
        else if (userHasEnoughBalance(userId)) {
            BookService bookService = BookServiceImpl.getInstance();
            try {
                if (!bookService.addBookToUser(userId, bookId)) {
                    request.setAttribute(AttributeName.GOT_BOOK_FAILED, true);
                } else {
                    if (!changeUsersBalance(userId)) {
                        if (!rollBackBook(userId, bookId)) {
                            return new Router(PagesPath.SERVER_ERROR);
                        }
                    } else {
                        request.setAttribute(AttributeName.GOT_BOOK_SUCCESS, true);
                    }
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            request.setAttribute(AttributeName.GOT_BOOK_FAILED, true);
        }
        return new GoToBookPageCommand().execute(request, response);
    }

    private boolean userIsBlocked(long userId) {
        UserService service = UserServiceImpl.getInstance();
        try {
            Optional<User> optionalUser = service.getUserById(userId);
            if (optionalUser.isEmpty()) {
                return true;
            } else {
                return optionalUser.get().isBlocked();
            }
        } catch (ServiceException e) {
            return true;
        }
    }

    private boolean userHasEnoughBalance(long userId) {
        UserService service = UserServiceImpl.getInstance();
        int usersBalance = 0;
        try {
            usersBalance = service.getUserBalance(userId);
        } catch (ServiceException e) {
            return false;
        }
        return usersBalance >= BOOK_PRICE;
    }

    private boolean changeUsersBalance(long userId) {
        UserService service = UserServiceImpl.getInstance();
        try {
            return service.subtractFromUsersBalance(userId, BOOK_PRICE);
        } catch (ServiceException e) {
            return false;
        }
    }

    private boolean rollBackBook(long userId, long bookId) throws CommandException {
        BookService bookService = BookServiceImpl.getInstance();
        try {
            if (!bookService.freeBookFromUser(userId, bookId)) {
                logger.error("Failed to rollback book.");
                return false;
            }
        } catch (ServiceException e) {
            logger.error("Failed to rollback book.");
            throw new CommandException(e);
        }
        return true;
    }

    private boolean numberOfBooksUserHasWithinALimit(long userId) throws CommandException {
        BookService bookService = BookServiceImpl.getInstance();
        try {
            return bookService.getBooksUserHas(userId).size() < MAX_NUMBER_OF_USERS_BOOKS;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
