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

import java.util.*;

public class AddAuthorCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = new Router(PagesPath.LIBRARIAN_ADD_AUTHOR);
        Set<String> invalids = new HashSet<>();
        AuthorService service = AuthorServiceImpl.getInstance();
        try{
            Map<String, String> authorMap = createAuthorMap(request);
            Optional<Author> author = service.addAuthor(authorMap, invalids);
            if(author.isPresent()){
                request.setAttribute(AttributeName.ADD_AUTHOR_SUCCESS, true);
            }
            else {
                addInvalidsToRequest(request, invalids);
            }
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }
        return router;
    }

    private Map<String, String> createAuthorMap(HttpServletRequest request){
        Map<String, String> authorMap = new HashMap<>();
        authorMap.put(ParameterName.FIRST_NAME, request.getParameter(ParameterName.FIRST_NAME));
        authorMap.put(ParameterName.LAST_NAME, request.getParameter(ParameterName.LAST_NAME));
        authorMap.put(ParameterName.BIOGRAPHY, request.getParameter(ParameterName.BIOGRAPHY));
        return authorMap;
    }

    private void addInvalidsToRequest(HttpServletRequest request, Set<String> invalids){
        for(String invalid : invalids){
            request.setAttribute(invalid, true);
        }
    }
}
