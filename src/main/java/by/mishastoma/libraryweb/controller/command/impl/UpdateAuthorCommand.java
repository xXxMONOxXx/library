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

import java.util.*;

public class UpdateAuthorCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Set<String> invalids = new HashSet<>();
        AuthorService service = AuthorServiceImpl.getInstance();
        try {
            Map<String, String> authorMap = createAuthorMap(request);
            if (service.updateAuthor(authorMap, invalids)) {
                request.setAttribute(AttributeName.UPDATE_AUTHOR_SUCCESS, true);
                logger.info("Author - {}, was updated", authorMap.get(ParameterName.AUTHOR_ID));
            } else {
                request.setAttribute(AttributeName.UPDATE_AUTHOR_FAILED, true);
                addInvalidsToRequest(request, invalids);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute(AttributeName.AUTHOR_ID, request.getParameter(ParameterName.AUTHOR_ID));
        return new GoToUpdateAuthorPageCommand().execute(request, response);
    }

    private Map<String, String> createAuthorMap(HttpServletRequest request) {
        Map<String, String> authorMap = new HashMap<>();
        authorMap.put(ParameterName.AUTHOR_ID, request.getParameter(ParameterName.AUTHOR_ID));
        authorMap.put(ParameterName.FIRST_NAME, request.getParameter(ParameterName.FIRST_NAME));
        authorMap.put(ParameterName.LAST_NAME, request.getParameter(ParameterName.LAST_NAME));
        authorMap.put(ParameterName.BIOGRAPHY, request.getParameter(ParameterName.BIOGRAPHY));
        return authorMap;
    }
}
