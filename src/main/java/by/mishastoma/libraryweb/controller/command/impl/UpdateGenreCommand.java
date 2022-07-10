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

public class UpdateGenreCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long genreId = Long.parseLong(request.getParameter(ParameterName.GENRE_ID));
        String genreName = request.getParameter(ParameterName.GENRE_NAME);
        GenreService genreService = GenreServiceImpl.getInstance();
        try {
            if (!genreService.updateGenre(genreId, genreName)) {
                request.setAttribute(AttributeName.UPDATE_GENRE_FAILED, true);
            } else {
                request.setAttribute(AttributeName.UPDATE_GENRE_SUCCESS, true);
                logger.info("Genre - {}, was updated.", genreId);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new GoToUpdateGenrePageCommand().execute(request, response);
    }
}
