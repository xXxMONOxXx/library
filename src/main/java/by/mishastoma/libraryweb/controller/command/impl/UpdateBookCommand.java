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

import java.io.IOException;
import java.util.*;

public class UpdateBookCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        request.setAttribute(AttributeName.BOOK_ID, request.getParameter(ParameterName.BOOK_ID));
        Set<String> invalids = new HashSet<>();
        BookService bookService = BookServiceImpl.getInstance();
        try {
            if (!bookService.updateBook(createBookMap(request), invalids)) {
                addInvalidsToRequest(request, invalids);
            } else {
                request.setAttribute(AttributeName.UPDATE_BOOK_SUCCESS, true);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new GoToUpdateBookPageCommand().execute(request, response);
    }

    private Map<String, Object> createBookMap(HttpServletRequest request) throws CommandException {
        Map<String, Object> bookMap = new HashMap<>();
        bookMap.put(ParameterName.BOOK_ID, request.getParameter(ParameterName.BOOK_ID));
        bookMap.put(ParameterName.BOOK_NAME, request.getParameter(ParameterName.BOOK_NAME));
        bookMap.put(ParameterName.BOOK_RELEASE_DATE, request.getParameter(ParameterName.BOOK_RELEASE_DATE));
        bookMap.put(ParameterName.BOOK_GENRES, request.getParameterValues(ParameterName.BOOK_GENRES));
        bookMap.put(ParameterName.BOOK_AUTHORS, request.getParameterValues(ParameterName.BOOK_AUTHORS));
        bookMap.put(ParameterName.BOOK_AGE_LIMITATIONS, request.getParameter(ParameterName.BOOK_AGE_LIMITATIONS));
        bookMap.put(ParameterName.BOOK_QUANTITY, request.getParameter(ParameterName.BOOK_QUANTITY));
        bookMap.put(ParameterName.BOOK_INFO, request.getParameter(ParameterName.BOOK_INFO));
        try {
            Part coverPhoto = request.getPart(ParameterName.BOOK_COVER_PHOTO);
            if (coverPhoto.getSize() != 0) {
                bookMap.put(ParameterName.BOOK_COVER_PHOTO, coverPhoto);
            } else if (request.getParameterValues(ParameterName.DELETE_COVER_PHOTO) != null) {
                bookMap.put(ParameterName.BOOK_COVER_PHOTO, null);
            }
        } catch (IOException | ServletException e) {
            throw new CommandException(e);
        }
        return bookMap;
    }
}
