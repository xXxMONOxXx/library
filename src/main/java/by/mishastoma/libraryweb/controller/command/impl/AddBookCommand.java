package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.entity.Book;
import by.mishastoma.libraryweb.model.service.BookService;
import by.mishastoma.libraryweb.model.service.impl.BookServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;

public class AddBookCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Set<String> invalids = new HashSet<>();
        BookService bookService = BookServiceImpl.getInstance();
        try {
            Optional<Book> optionalBook = bookService.addBook(createBookMap(request), invalids);
            if (optionalBook.isEmpty()) {
                addInvalidsToRequest(request, invalids);
            } else {
                request.setAttribute(AttributeName.ADD_BOOK_SUCCESS, true);
                logger.info("Created new book.");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new GoToAddBookPageCommand().execute(request, response);
    }

    private Map<String, Object> createBookMap(HttpServletRequest request) throws CommandException {
        Map<String, Object> bookMap = new HashMap<>();
        bookMap.put(ParameterName.BOOK_NAME, request.getParameter(ParameterName.BOOK_NAME));
        bookMap.put(ParameterName.BOOK_RELEASE_DATE, request.getParameter(ParameterName.BOOK_RELEASE_DATE));
        bookMap.put(ParameterName.BOOK_GENRES, request.getParameterValues(ParameterName.BOOK_GENRES));
        bookMap.put(ParameterName.BOOK_AUTHORS, request.getParameterValues(ParameterName.BOOK_AUTHORS));
        bookMap.put(ParameterName.BOOK_AGE_LIMITATIONS, request.getParameter(ParameterName.BOOK_AGE_LIMITATIONS));
        bookMap.put(ParameterName.BOOK_QUANTITY, request.getParameter(ParameterName.BOOK_QUANTITY));
        bookMap.put(ParameterName.BOOK_INFO, request.getParameter(ParameterName.BOOK_INFO));
        try {
            Part coverPhoto = request.getPart(ParameterName.BOOK_COVER_PHOTO);
            if (coverPhoto != null) {
                bookMap.put(ParameterName.BOOK_COVER_PHOTO, coverPhoto);
            }
        } catch (IOException | ServletException e) {
            throw new CommandException(e);
        }
        return bookMap;
    }
}
