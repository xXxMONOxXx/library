package by.mishastoma.libraryweb.controller.command.impl;

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

import java.util.Optional;

public class GoToBookPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        BookService service = BookServiceImpl.getInstance();
        try {
            long id = Long.parseLong(request.getParameter(ParameterName.BOOK_ID));
            Optional<Book> optionalBook = service.getBookById(id);
            if (optionalBook.isPresent()) {
                request.setAttribute(ParameterName.BOOK, optionalBook.get());
                return new Router(PagesPath.BOOK);
            } else {
                return new Router(PagesPath.NOT_FOUND);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }


    }
}
