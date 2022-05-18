package by.mishastoma.libraryweb.controller;

import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.controller.command.CommandType;
import by.mishastoma.libraryweb.exception.CommandException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "Controller", value = {"/controller"})
public class Controller extends HttpServlet {

    public void init(){
        //todo
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String commandStr = request.getParameter("command");
        Command command = CommandType.define(commandStr);
        String page = null;
        try {
            page = command.execute(request);
            request.getRequestDispatcher(page).forward(request, response);
        } catch (CommandException e) {
            // todo add errors
            request.setAttribute("error_msg", e.getCause()); // 3
            request.getRequestDispatcher("pages/errors/500.jsp").forward(request, response); // 3
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }
    public void destroy(){
        //todo
    }
}
