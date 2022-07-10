package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.PagesPath;
import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.entity.Book;
import by.mishastoma.libraryweb.model.service.BookService;
import by.mishastoma.libraryweb.model.service.impl.BookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class GoToAllBooksPageCommand implements Command {

    private static final int PAGE_SIZE = 2;

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        BookService service = BookServiceImpl.getInstance();
        List<Book> books;
        try {
            String pageStr = request.getParameter(ParameterName.PAGE);
            int page = pageStr == null ? 1 : Integer.parseInt(pageStr);
            int numberOfBooks = service.countNumberOfBooks();
            int numberOfPages = (int) Math.ceil(numberOfBooks * 1.0 / PAGE_SIZE);
            books = service.getAmount((page - 1) * PAGE_SIZE, PAGE_SIZE);
            request.setAttribute(AttributeName.CURRENT_PAGE, page);
            request.setAttribute(AttributeName.NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(AttributeName.BOOKS_LIST, books);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(PagesPath.HOME);
    }
}
