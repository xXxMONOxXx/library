package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.controller.pool.service.UserService;
import by.mishastoma.libraryweb.controller.pool.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SignInCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String signInLogin = request.getParameter("login");
        String password = request.getParameter("password");
        UserService userService = UserServiceImpl.getInstance();
        String page;
        HttpSession session = request.getSession();
        try {
            if(userService.authenticate(signInLogin,password)){
                request.setAttribute("login", signInLogin);
                session.setAttribute("login", signInLogin);
                page = "pages/home/home.jsp";
            }
            else{
                request.setAttribute("sign_in_msg", "Incorrect login or password.");
                page = "pages/entry/sign_in.jsp";
            }
            session.setAttribute("current_page", page);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
