package by.mishastoma.libraryweb.validator;

import jakarta.servlet.http.Part;

import java.io.InputStream;

public interface BookValidator {
    boolean isValidName(String name);
    boolean isValidReleaseDate(String releaseDate);
    boolean isValidAbout(String about);
    boolean isValidQuantity(String quantity);
    boolean isValidAgeLimitations(String ageLimitations);
    boolean isValidPicture(Part file);
    boolean isValidIdsArray(String [] ids);
}
