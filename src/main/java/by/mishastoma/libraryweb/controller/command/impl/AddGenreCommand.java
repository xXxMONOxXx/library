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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class AddGenreCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = new Router(PagesPath.LIBRARIAN_ADD_GENRE);
        GenreService service = GenreServiceImpl.getInstance();
        try {
            Optional<Genre> genre = service.addGenre(request.getParameter(ParameterName.GENRE_NAME));
            if (genre.isPresent()) {
                request.setAttribute(AttributeName.ADD_GENRE_SUCCESS, true);
                logger.info("Added new genre.");
            } else {
                request.setAttribute(AttributeName.ADD_GENRE_INVALID_NAME, true);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
