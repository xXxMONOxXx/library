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

import java.util.Optional;

public class GoToUpdateGenrePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long genreId = Long.parseLong(request.getParameter(ParameterName.GENRE_ID));
        GenreService genreService = GenreServiceImpl.getInstance();
        try {
            Optional<Genre> optionalGenre = genreService.getById(genreId);
            if(optionalGenre.isEmpty()){
                request.setAttribute(AttributeName.FAILED_TO_GET_GENRE, true);
                return new GetAllGenresCommand().execute(request, response);
            }
            request.setAttribute(AttributeName.GENRE, optionalGenre.get());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(PagesPath.UPDATE_GENRE);
    }
}
