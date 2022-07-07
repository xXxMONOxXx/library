package by.mishastoma.libraryweb.controller.command;

import by.mishastoma.libraryweb.controller.command.impl.*;
import com.mysql.cj.util.StringUtils;

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    SIGN_OUT(new SignOutCommand()),
    DEFAULT(new DefaultCommand()),
    GET_ALL_USERS(new GetAllUsersCommand()),
    GO_TO_ADD_BOOK_PAGE(new GoToAddBookPageCommand()),
    ADD_BOOK(new AddBookCommand()),
    GET_USER_INFO_BY_ID(new GetUserInfoByIdCommand()),
    ADD_AUTHOR (new AddAuthorCommand()),
    ADD_GENRE(new AddGenreCommand()),
    GET_ALL_GENRES (new GetAllGenresCommand()),
    GET_ALL_AUTHORS(new GetAllAuthorsCommand()),
    GO_TO_BOOK_PAGE(new GoToBookPageCommand()),
    GO_TO_AUTHOR_PAGE (new GoToAuthorPageCommand()),
    ADD_BALANCE_TO_USER(new AddBalanceToUserCommand()),
    ADD_BOOK_TO_USER (new AddBookToUserCommand()),
    RETURN_BOOK (new ReturnBookCommand()),
    GO_TO_ALL_BOOKS_PAGE(new GoToAllBooksPageCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String commandStr) {
        CommandType current = DEFAULT;
        if (!StringUtils.isNullOrEmpty(commandStr)) {
            commandStr = commandStr.toUpperCase();
            for (CommandType type : CommandType.values()) {
                if (type.name().equals(commandStr)) {
                    current = CommandType.valueOf(commandStr.toUpperCase());
                }
            }
        }
        return current.command;
    }
}
