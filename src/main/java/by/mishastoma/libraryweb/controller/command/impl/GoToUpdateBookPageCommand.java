package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.PagesPath;
import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.entity.Book;
import by.mishastoma.libraryweb.model.service.AuthorService;
import by.mishastoma.libraryweb.model.service.BookService;
import by.mishastoma.libraryweb.model.service.GenreService;
import by.mishastoma.libraryweb.model.service.impl.AuthorServiceImpl;
import by.mishastoma.libraryweb.model.service.impl.BookServiceImpl;
import by.mishastoma.libraryweb.model.service.impl.GenreServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class GoToUpdateBookPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long bookId = Long.parseLong(request.getParameter(ParameterName.BOOK_ID));
        BookService bookService = BookServiceImpl.getInstance();
        try{
            Optional<Book> optionalBook = bookService.getBookById(bookId);
            if(optionalBook.isEmpty()){
                request.setAttribute(AttributeName.FAILED_TO_GET_BOOK, true);
                return new GoToAllBooksPageCommand().execute(request, response);
            }
            else{
                AuthorService authorService = AuthorServiceImpl.getInstance();
                GenreService genreService = GenreServiceImpl.getInstance();
                request.setAttribute(AttributeName.AUTHORS_LIST, authorService.getAll());
                request.setAttribute(AttributeName.GENRES_LIST, genreService.getAll());
                request.setAttribute(AttributeName.ACTUAL_QUANTITY, bookService.getActualBooksQuantity(bookId));
                request.setAttribute(AttributeName.BOOK, optionalBook.get());
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(PagesPath.UPDATE_BOOK);
    }
}
