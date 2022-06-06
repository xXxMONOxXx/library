package by.mishastoma.libraryweb.controller.command;

import by.mishastoma.libraryweb.controller.command.impl.*;
import com.mysql.cj.util.StringUtils;

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    SIGN_OUT(new SignOutCommand()),
    DEFAULT(new DefaultCommand()),
    GET_ALL_USERS(new GetAllUsers());

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
