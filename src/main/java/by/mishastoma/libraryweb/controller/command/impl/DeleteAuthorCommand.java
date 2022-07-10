package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.service.AuthorService;
import by.mishastoma.libraryweb.model.service.impl.AuthorServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteAuthorCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long authorId = Long.parseLong(request.getParameter(ParameterName.AUTHOR_ID));
        AuthorService authorService = AuthorServiceImpl.getInstance();
        try {
            if(!authorService.deleteAuthor(authorId)){
                request.setAttribute(AttributeName.FAILED_TO_DELETE_AUTHOR, true);
                return new GoToUpdateAuthorPageCommand().execute(request, response);
            }
            else{
                request.setAttribute(AttributeName.DELETE_AUTHOR_SUCCESS, true);
                logger.info("Deleted author.");
                return new GetAllAuthorsCommand().execute(request, response);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
