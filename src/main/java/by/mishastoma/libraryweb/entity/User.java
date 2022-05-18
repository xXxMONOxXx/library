package by.mishastoma.libraryweb.entity;

import java.time.LocalDate;

public class User extends AbstractEntity{
    private String login;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate birthdate;
    private UserRole role;
    private UserState state;

    public User(long id) {
        super(id);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public static class Builder {

        private User newUser;

        public Builder() {
            newUser = new User(-1); //todo
        }

       public Builder withLogin(String login){
            newUser.login = login;
            return this;
       }

       public Builder withPassword(String password){
            newUser.password=password;
            return this;
       }

       public Builder withFirstName(String firstname){
            newUser.firstname = firstname;
            return this;
       }

       public Builder withLastName(String lastname){
            newUser.lastname = lastname;
            return this;
       }

       public Builder withEmail(String email){
            newUser.email=email;
            return this;
       }

       public Builder withBirthdate(LocalDate birthdate){
            newUser.birthdate=birthdate;
            return this;
       }

        public User build() {
            return newUser;
        }
    }
}
