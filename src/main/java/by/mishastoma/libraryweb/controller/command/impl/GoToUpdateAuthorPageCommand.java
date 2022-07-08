package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.PagesPath;
import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.entity.Author;
import by.mishastoma.libraryweb.model.service.AuthorService;
import by.mishastoma.libraryweb.model.service.impl.AuthorServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class GoToUpdateAuthorPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long authorId = Long.parseLong(request.getParameter(ParameterName.AUTHOR_ID));
        AuthorService authorService = AuthorServiceImpl.getInstance();
        try {
            Optional<Author> optionalAuthor = authorService.getById(authorId);
            if(optionalAuthor.isEmpty()){
                request.setAttribute(AttributeName.FAILED_TO_GET_AUTHOR, true);
                return new GetAllAuthorsCommand().execute(request, response);
            }
            request.setAttribute(AttributeName.AUTHOR, optionalAuthor.get());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(PagesPath.UPDATE_AUTHOR);
    }
}
