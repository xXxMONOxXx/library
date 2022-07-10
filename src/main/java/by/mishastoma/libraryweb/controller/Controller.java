package by.mishastoma.libraryweb.controller;

import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.controller.command.CommandType;
import by.mishastoma.libraryweb.controller.pool.ConnectionPool;
import by.mishastoma.libraryweb.exception.CommandException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "Controller", value = {"/controller"})
@MultipartConfig(maxFileSize = 16177215) // upload file's size up to 16MB
public class Controller extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void init() {
        ConnectionPool.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandType.define(request.getParameter(ParameterName.COMMAND));
        try {
            Router router = command.execute(request, response);
            switch (router.getType()) {
                case FORWARD -> request.getRequestDispatcher(router.getPage()).forward(request, response);
                case REDIRECT -> response.sendRedirect(router.getPage());
                default -> throw new ServletException("Non existing command.");
            }
        } catch (CommandException e) {
            logger.fatal(e);
            throw new ServletException(e);
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }
}
