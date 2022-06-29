package by.mishastoma.libraryweb.validator;

import by.mishastoma.libraryweb.model.dao.TableColumn;

public interface AuthorValidator {

    boolean isValidFirstName(String firstname);
    boolean isValidLastName(String lastname);
    boolean isValidBiography(String biography);
}
