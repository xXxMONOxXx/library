package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.service.BookService;
import by.mishastoma.libraryweb.model.service.impl.BookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DeleteBookCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long bookId = Long.parseLong(request.getParameter(ParameterName.BOOK_ID));
        BookService bookService = BookServiceImpl.getInstance();
        try {
            if(!bookService.deleteBook(bookId)){
                request.setAttribute(AttributeName.FAILED_TO_DELETE_BOOK, true);
                return new GoToUpdateBookPageCommand().execute(request, response);
            }
            else{
                request.setAttribute(AttributeName.DELETE_BOOK_SUCCESS, true);
                logger.info("Deleted book.");
                return new GoToAllBooksPageCommand().execute(request, response);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
