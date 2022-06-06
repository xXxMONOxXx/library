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

import java.util.*;


public class SignUpCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = new Router();
        Set<String> invalids = new HashSet<>();
        UserService service = UserServiceImpl.getInstance();
        try{
            Optional<User> user = service.signUp(request, invalids);
            if(user.isPresent()){

                router.setPage(PagesPath.HOME);
                //todo add session
            }
            else{
                addInvalidsToRequest(request, invalids);
                router.setPage(PagesPath.ENTRY_SIGN_UP);
            }
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }
        return router;
    }

    private void addInvalidsToRequest(HttpServletRequest request, Set<String> invalids){
        //todo not best approach request.setAttribute(AttributeName.SIGN_UP_LOGIN_IS_INVALID, invalids.contains(ParameterName.SIGN_UP_LOGIN_IS_VALID);
        if(invalids.contains(ParameterName.SIGN_UP_LOGIN_IS_INVALID)){
            request.setAttribute(AttributeName.SIGN_UP_LOGIN_IS_INVALID, true);
        }
        if(invalids.contains(ParameterName.SIGN_UP_FIRSTNAME_IS_INVALID)){
            request.setAttribute(AttributeName.SIGN_UP_FIRSTNAME_IS_INVALID, true);
        }
        if(invalids.contains(ParameterName.SIGN_UP_LASTNAME_IS_INVALID)){
            request.setAttribute(AttributeName.SIGN_UP_LASTNAME_IS_INVALID, true);
        }
        if(invalids.contains(ParameterName.SIGN_UP_EMAIL_IS_INVALID)){
            request.setAttribute(AttributeName.SIGN_UP_EMAIL_IS_INVALID, true);
        }
        if(invalids.contains(ParameterName.SIGN_UP_BIRTHDATE_IS_INVALID)){
            request.setAttribute(AttributeName.SIGN_UP_BIRTHDATE_IS_INVALID, true);
        }
        if(invalids.contains(ParameterName.SIGN_UP_PASSWORD_IS_INVALID)){
            request.setAttribute(AttributeName.SIGN_UP_PASSWORD_IS_INVALID, true);
        }
        if(invalids.contains(ParameterName.SIGN_UP_PASSWORD_CONFIRM_IS_INVALID)){
            request.setAttribute(AttributeName.SIGN_UP_PASSWORD_CONFIRM_IS_INVALID, true);
        }
    }
}
