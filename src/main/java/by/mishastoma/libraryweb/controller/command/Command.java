package by.mishastoma.libraryweb.controller.command;

import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Set;

@FunctionalInterface
public interface Command {
    Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
    default void refresh(){}

    default void addInvalidsToRequest(HttpServletRequest request, Set<String> invalids){
        for(String invalid : invalids){
            request.setAttribute(invalid, true);
        }
    }
}
