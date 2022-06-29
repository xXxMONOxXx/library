package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.PagesPath;
import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.entity.Genre;
import by.mishastoma.libraryweb.model.service.GenreService;
import by.mishastoma.libraryweb.model.service.impl.GenreServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class GetAllGenresCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        //todo validate session
        GenreService service = GenreServiceImpl.getInstance();
        try {
            List<Genre> genres = service.findAll();
            request.setAttribute(AttributeName.GENRES_LIST, genres);
            router.setPage(PagesPath.LIBRARIAN_GENRES);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
