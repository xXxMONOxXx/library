package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.dao.UserDao;
import by.mishastoma.libraryweb.dao.impl.UserDaoImpl;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class GetAllUsers implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        //
        String page;
        HttpSession session = request.getSession();
        try {
            UserDaoImpl dao = UserDaoImpl.getInstance(); //todo change
            request.setAttribute("users_list", dao.findAll());
            page = "pages/admin/users.jsp";
        } catch (DaoException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
