package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return "index.jsp";
    }
}
