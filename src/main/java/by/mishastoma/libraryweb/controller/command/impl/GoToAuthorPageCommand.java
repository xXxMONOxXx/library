package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.PagesPath;
import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.entity.Author;
import by.mishastoma.libraryweb.model.entity.Book;
import by.mishastoma.libraryweb.model.service.AuthorService;
import by.mishastoma.libraryweb.model.service.BookService;
import by.mishastoma.libraryweb.model.service.impl.AuthorServiceImpl;
import by.mishastoma.libraryweb.model.service.impl.BookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

public class GoToAuthorPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        BookService bookService = BookServiceImpl.getInstance();
        AuthorService authorService = AuthorServiceImpl.getInstance();
        try {
            long authorId = Long.parseLong(request.getParameter(ParameterName.AUTHOR_ID));
            List<Book> books = bookService.getBooksByAuthorsId(authorId);
            Optional<Author> optionalAuthor = authorService.getById(authorId);
            if (optionalAuthor.isEmpty()) {
                return new Router(PagesPath.NOT_FOUND);
            }
            request.setAttribute(AttributeName.BOOKS_LIST, books);
            request.setAttribute(AttributeName.AUTHOR, optionalAuthor.get());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(PagesPath.AUTHOR);
    }
}
