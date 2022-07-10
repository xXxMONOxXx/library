package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.PagesPath;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.entity.Author;
import by.mishastoma.libraryweb.model.service.AuthorService;
import by.mishastoma.libraryweb.model.service.impl.AuthorServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class GetAllAuthorsCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = new Router();
        AuthorService service = AuthorServiceImpl.getInstance();
        try {
            List<Author> authors = service.getAll();
            request.setAttribute(AttributeName.AUTHORS_LIST, authors);
            router.setPage(PagesPath.LIBRARIAN_AUTHORS);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
