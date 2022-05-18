package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.dao.impl.UserDaoImpl;
import by.mishastoma.libraryweb.entity.User;
import by.mishastoma.libraryweb.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;


public class SignUpCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String[] date = request.getParameter("birthdate").split("-");
        User user = new User.Builder()
                .withLogin(request.getParameter("login"))
                .withPassword(request.getParameter("password"))
                .withFirstName(request.getParameter("firstname"))
                .withLastName(request.getParameter("lastname"))
                .withEmail(request.getParameter("email"))
                .withBirthdate(LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]),Integer.parseInt(date[2])))
                .build();
        String page = null;
        try{
            UserDaoImpl dao = UserDaoImpl.getInstance(); //todo switch to userdao instead
            if(dao.insert(user)){
                page = "pages/home/home.jsp";
            }
            else{
                request.setAttribute("sign_up_msg", "Login or email is already in use.");
                page = "pages/entry/sign_up.jsp";
            }
        }
        catch (Exception e){
            throw new CommandException(e);
            //todo
        }
        return page;
    }
}
