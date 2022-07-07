package by.mishastoma.libraryweb.controller.command.impl;

import by.mishastoma.libraryweb.controller.AttributeName;
import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.controller.Router;
import by.mishastoma.libraryweb.controller.command.Command;
import by.mishastoma.libraryweb.exception.CommandException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.service.BookService;
import by.mishastoma.libraryweb.model.service.impl.BookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ReturnBookCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long userId = Long.parseLong(request.getParameter(ParameterName.USER_ID));
        long bookId = Long.parseLong(request.getParameter(ParameterName.BOOK_ID));
        request.setAttribute(AttributeName.USER_ID, userId);
        BookService bookService = BookServiceImpl.getInstance();
        try {
            if(bookService.freeBookFromUser(userId, bookId)){
                request.setAttribute(AttributeName.RETURNED_BOOK_SUCCESS, true);
            }
            else{
                request.setAttribute(AttributeName.RETURN_BOOK_FAILED, true);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new GetUserInfoByIdCommand().execute(request, response);
    }
}
