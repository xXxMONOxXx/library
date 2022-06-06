package by.mishastoma.libraryweb.model.entity;

import java.time.LocalDate;

public class User extends AbstractEntity {
    private String login;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate birthdate;
    private UserRole role;
    private boolean isBlocked;

    public User(long id) {
        super(id);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) { //todo remove
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password; //todo remove
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email; //todo remove
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }


    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role; //todo remove
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setStatus(boolean status) {
        this.isBlocked = status;
    }

    public static class Builder {

        private User newUser;

        public Builder(long id) {
            newUser = new User(id);
        }

        public Builder withLogin(String login) {
            newUser.login = login;
            return this;
        }

        public Builder withPassword(String password) {
            newUser.password = password;
            return this;
        }

        public Builder withFirstName(String firstname) {
            newUser.firstname = firstname;
            return this;
        }

        public Builder withLastName(String lastname) {
            newUser.lastname = lastname;
            return this;
        }

        public Builder withRole(UserRole role) {
            newUser.role = role;
            return this;
        }

        public Builder withStatus(boolean status){
            newUser.isBlocked = status;
            return this;
        }

        public Builder withEmail(String email) {
            newUser.email = email;
            return this;
        }

        public Builder withBirthdate(LocalDate birthdate) {
            newUser.birthdate = birthdate;
            return this;
        }

        public User build() {
            return newUser;
        }
    }
}
