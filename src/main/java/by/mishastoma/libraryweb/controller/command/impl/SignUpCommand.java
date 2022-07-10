package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.PagesPath;
import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.entity.User;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.model.service.UserService;
import by.mishastoma.libraryweb.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


public class SignUpCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Set<String> invalids = new HashSet<>();
        UserService service = UserServiceImpl.getInstance();
        try {
            Optional<User> user = service.signUp(createUserMap(request), invalids);
            if (user.isPresent()) {
                logger.info("User - {}, have been registered.", user.get().getId());
                HttpSession session = request.getSession();
                session.setAttribute(AttributeName.USER_ID, user.get().getId());
                session.setAttribute(AttributeName.ROLE, user.get().getRole().toString());
                return new GoToAllBooksPageCommand().execute(request, response);
            } else {
                addInvalidsToRequest(request, invalids);
                return new Router(PagesPath.ENTRY_SIGN_UP);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private Map<String, String> createUserMap(HttpServletRequest request) {
        Map<String, String> userMap = new HashMap<>();
        userMap.put(ParameterName.LOGIN, request.getParameter(ParameterName.LOGIN));
        userMap.put(ParameterName.FIRST_NAME, request.getParameter(ParameterName.FIRST_NAME));
        userMap.put(ParameterName.LAST_NAME, request.getParameter(ParameterName.LAST_NAME));
        userMap.put(ParameterName.EMAIL, request.getParameter(ParameterName.EMAIL));
        userMap.put(ParameterName.BIRTHDATE, request.getParameter(ParameterName.BIRTHDATE));
        userMap.put(ParameterName.PASSWORD, request.getParameter(ParameterName.PASSWORD));
        userMap.put(ParameterName.PASSWORD_REPEAT, request.getParameter(ParameterName.PASSWORD_REPEAT));
        return userMap;
    }
}
