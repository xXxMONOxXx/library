package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.PagesPath;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return new Router(PagesPath.NOT_FOUND, Router.Type.FORWARD);
    }
}
