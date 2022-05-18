package by.mishastoma.libraryweb.controller.command;

import by.mishastoma.libraryweb.controller.command.impl.DefaultCommand;
import by.mishastoma.libraryweb.controller.command.impl.GetAllUsers;
import by.mishastoma.libraryweb.controller.command.impl.SignInCommand;
import by.mishastoma.libraryweb.controller.command.impl.SignUpCommand;

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    DEFAULT(new DefaultCommand()),
    GET_ALL_USERS(new GetAllUsers());
    private Command command;
    CommandType(Command command) {
        this.command = command;
    }
    public static Command define(String commandStr){
        //todo null error + other stuff
        CommandType current = CommandType.valueOf(commandStr.toUpperCase());
        return current.command;
    }
}
