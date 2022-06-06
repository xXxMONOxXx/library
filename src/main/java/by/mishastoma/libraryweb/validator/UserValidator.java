package by.mishastoma.libraryweb.validator;

public interface UserValidator {

    boolean isValidLogin(String login);
    boolean isValidFirstName(String firstName);
    boolean isValidLastName(String lastName);
    boolean isValidPassword(String password);
    boolean isValidEmail(String email);
    boolean isValidBirthDate(String birthdate);
}
