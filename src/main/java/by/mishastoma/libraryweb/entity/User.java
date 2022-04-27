package by.mishastoma.libraryweb.entity;

import java.util.Date;

public class User extends AbstractEntity{
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthdate;
    private String phone;
    private UserRole role;
    private UserState state;

    public User(long id) {
        super(id); //todo create builder mb? create getters and setters
    }

}
