package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.PagesPath;
import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.model.dao.BaseDao;
import by.mishastoma.libraryweb.model.dao.UserDao;
import by.mishastoma.libraryweb.model.dao.impl.UserDaoImpl;
import by.mishastoma.libraryweb.model.entity.User;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class GetAllUsers implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Router router = new Router();
        HttpSession session = request.getSession();
        //todo validate session
        try {
            UserDao dao = UserDaoImpl.getInstance();
            List<User> users = dao.findAll();
            request.setAttribute(ParameterName.USERS_LIST, users);
            router.setPage(PagesPath.ADMIN_USERS);
        } catch (DaoException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
