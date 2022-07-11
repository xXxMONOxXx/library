package by.mishastoma.libraryweb.controller.filter;

import by.mishastoma.libraryweb.controller.command.CommandType;

import java.util.Set;

public enum UserRolePermission {
    GUEST(Set.of(CommandType.DEFAULT,
            CommandType.GET_ALL_AUTHORS,
            CommandType.GET_ALL_GENRES,
            CommandType.GO_TO_ALL_BOOKS_PAGE,
            CommandType.GO_TO_AUTHOR_PAGE,
            CommandType.GO_TO_BOOK_PAGE,
            CommandType.SET_LOCALIZATION,
            CommandType.SIGN_UP,
            CommandType.SIGN_IN)),
    USER(Set.of(CommandType.DEFAULT,
            CommandType.ADD_BOOK_TO_USER,
            CommandType.GO_TO_BOOK_PAGE,
            CommandType.GET_ALL_AUTHORS,
            CommandType.GET_ALL_GENRES,
            CommandType.GO_TO_ALL_BOOKS_PAGE,
            CommandType.GO_TO_AUTHOR_PAGE,
            CommandType.SET_LOCALIZATION,
            CommandType.ADD_BALANCE_TO_USER,
            CommandType.CHANGE_USERS_PASSWORD,
            CommandType.GET_USER_INFO_BY_ID,
            CommandType.RETURN_BOOK,
            CommandType.SIGN_OUT)),
    LIBRARIAN(Set.of(CommandType.DEFAULT,
            CommandType.ADD_BOOK_TO_USER,
            CommandType.GO_TO_BOOK_PAGE,
            CommandType.GET_ALL_AUTHORS,
            CommandType.GET_ALL_GENRES,
            CommandType.GO_TO_ALL_BOOKS_PAGE,
            CommandType.GO_TO_AUTHOR_PAGE,
            CommandType.SET_LOCALIZATION,
            CommandType.ADD_BALANCE_TO_USER,
            CommandType.CHANGE_USERS_PASSWORD,
            CommandType.GET_USER_INFO_BY_ID,
            CommandType.RETURN_BOOK,
            CommandType.SIGN_OUT,
            CommandType.ADD_AUTHOR,
            CommandType.ADD_BOOK,
            CommandType.ADD_GENRE,
            CommandType.GO_TO_UPDATE_AUTHOR_PAGE,
            CommandType.GO_TO_UPDATE_GENRE_PAGE,
            CommandType.GO_TO_UPDATE_BOOK_PAGE,
            CommandType.UPDATE_AUTHOR,
            CommandType.UPDATE_GENRE,
            CommandType.GO_TO_ADD_BOOK_PAGE,
            CommandType.DELETE_AUTHOR,
            CommandType.DELETE_BOOK,
            CommandType.DELETE_GENRE,
            CommandType.UPDATE_BOOK)),
    ADMIN(Set.of(CommandType.DEFAULT,
            CommandType.ADD_BOOK_TO_USER,
            CommandType.DELETE_AUTHOR,
            CommandType.DELETE_BOOK,
            CommandType.DELETE_GENRE,
            CommandType.GO_TO_ADD_BOOK_PAGE,
            CommandType.GO_TO_BOOK_PAGE,
            CommandType.GET_ALL_AUTHORS,
            CommandType.GET_ALL_GENRES,
            CommandType.GO_TO_ALL_BOOKS_PAGE,
            CommandType.GO_TO_AUTHOR_PAGE,
            CommandType.SET_LOCALIZATION,
            CommandType.ADD_BALANCE_TO_USER,
            CommandType.CHANGE_USERS_PASSWORD,
            CommandType.GET_USER_INFO_BY_ID,
            CommandType.RETURN_BOOK,
            CommandType.SIGN_OUT,
            CommandType.ADD_AUTHOR,
            CommandType.ADD_BOOK,
            CommandType.ADD_GENRE,
            CommandType.GO_TO_UPDATE_AUTHOR_PAGE,
            CommandType.GO_TO_UPDATE_GENRE_PAGE,
            CommandType.GO_TO_UPDATE_BOOK_PAGE,
            CommandType.UPDATE_AUTHOR,
            CommandType.UPDATE_GENRE,
            CommandType.UPDATE_BOOK,
            CommandType.BLOCK_USER,
            CommandType.UNBLOCK_USER,
            CommandType.GET_ALL_USERS,
            CommandType.CHANGE_USERS_ROLE
    ));

    private final Set<CommandType> commands;

    UserRolePermission(Set<CommandType> commands) {
        this.commands = commands;
    }

    public boolean isAllowed(CommandType command) {
        return commands.stream().anyMatch(command::equals);
    }
}
