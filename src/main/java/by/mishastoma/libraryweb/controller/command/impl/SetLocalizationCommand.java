package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.*;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SetLocalizationCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String locale = request.getParameter(ParameterName.LANGUAGE);
        if(!isValidLanguage(locale)){
            request.setAttribute(AttributeName.INVALID_LOCALE, true);
        }
        else {
            HttpSession session = request.getSession();
            session.setAttribute(AttributeName.LANGUAGE, locale);
        }
        return new Router(PagesPath.INDEX, Router.Type.REDIRECT);
    }

    private boolean isValidLanguage(String locale){
        if(locale!=null){
            return locale.equals(LocaleType.RUSSIAN) || locale.equals(LocaleType.ENGLISH);
        }
        return false;
    }
}
