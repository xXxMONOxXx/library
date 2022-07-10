package by.mishastoma.libraryweb.validator;


public interface AuthorValidator {

    boolean isValidFirstName(String firstname);

    boolean isValidLastName(String lastname);

    boolean isValidBiography(String biography);
}
