package by.mishastoma.libraryweb.controller.command;

import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface Command {
    Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
    default void refresh(){}
}
