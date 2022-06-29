package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.PagesPath;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.service.AuthorService;
import by.mishastoma.libraryweb.model.service.GenreService;
import by.mishastoma.libraryweb.model.service.impl.AuthorServiceImpl;
import by.mishastoma.libraryweb.model.service.impl.GenreServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToAddBookPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            AuthorService authorService = AuthorServiceImpl.getInstance();
            GenreService genreService = GenreServiceImpl.getInstance();
            request.setAttribute(AttributeName.AUTHORS_LIST, authorService.findAll());
            request.setAttribute(AttributeName.GENRES_LIST, genreService.findAll());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(PagesPath.LIBRARIAN_ADD_BOOK, Router.Type.FORWARD);
    }
}
