package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.service.GenreService;
import by.mishastoma.libraryweb.model.service.impl.GenreServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteGenreCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long genreId = Long.parseLong(request.getParameter(ParameterName.GENRE_ID));
        GenreService genreService = GenreServiceImpl.getInstance();
        try {
            if (!genreService.deleteGenre(genreId)) {
                request.setAttribute(AttributeName.FAILED_TO_DELETE_GENRE, true);
                return new GoToUpdateGenrePageCommand().execute(request, response);
            } else {
                request.setAttribute(AttributeName.DELETE_GENRE_SUCCESS, true);
                logger.info("Deleted genre.");
                return new GetAllGenresCommand().execute(request, response);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
