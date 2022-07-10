package by.mishastoma.libraryweb.validator.impl;

import by.mishastoma.libraryweb.validator.BookValidator;
import com.mysql.cj.util.StringUtils;
import jakarta.servlet.http.Part;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class BookValidatorImpl implements BookValidator {

    private static final String BOOK_NAME_REGEX = "^([A-Za-zА-Яа-я]+\\s)*[A-Za-zА-Яа-я]+${3,64}$";
    private static final String RELEASE_DATE_REGEX = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
    private static final int ABOUT_MAX_SIZE = 1000;
    private static final String NUMBER_REGEX = "^[0-9]{1,3}$";
    private static final String IMAGE_TYPE = "image/png";
    private static final int SIXTEEN_MB = 16777216;

    private BookValidatorImpl() {

    }

    public static BookValidatorImpl instance;

    public static BookValidatorImpl getInstance() {
        if (instance == null) {
            instance = new BookValidatorImpl();
        }
        return instance;
    }

    @Override
    public boolean isValidName(String name) {
        if (StringUtils.isEmptyOrWhitespaceOnly(name)) {
            return false;
        }
        Pattern pattern = Pattern.compile(BOOK_NAME_REGEX);
        return pattern.matcher(name).matches();
    }

    @Override
    public boolean isValidReleaseDate(String releaseDate) {
        if (StringUtils.isEmptyOrWhitespaceOnly(releaseDate)) {
            return false;
        }
        Pattern pattern = Pattern.compile(RELEASE_DATE_REGEX);
        if (!pattern.matcher(releaseDate).matches()) {
            return false;
        }
        try {
            LocalDate date = LocalDate.parse(releaseDate);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public boolean isValidAbout(String about) {
        if (!StringUtils.isEmptyOrWhitespaceOnly(about)) {
            return about.length() < ABOUT_MAX_SIZE;
        }
        return true;
    }

    @Override
    public boolean isValidQuantity(String quantity) {
        if (StringUtils.isEmptyOrWhitespaceOnly(quantity)) {
            return false;
        }
        Pattern pattern = Pattern.compile(NUMBER_REGEX);
        return pattern.matcher(quantity).matches();
    }

    @Override
    public boolean isValidAgeLimitations(String ageLimitations) {
        if (StringUtils.isEmptyOrWhitespaceOnly(ageLimitations)) {
            return true;
        }
        Pattern pattern = Pattern.compile(NUMBER_REGEX);
        return pattern.matcher(ageLimitations).matches();
    }

    @Override
    public boolean isValidPicture(Part file) {
        if (file != null) {
            if (file.getSize() != -1 && file.getSize() != 0) {
                if (!IMAGE_TYPE.equals(file.getContentType())) {
                    return false;
                }
                return file.getSize() <= SIXTEEN_MB;
            }
        }
        return true;
    }

    @Override
    public boolean isValidIdsArray(String[] ids) {
        if (ids == null) {
            return true;
        }
        if (ids.length <= 0) {
            return false;
        }
        for (String id : ids) {
            if (!StringUtils.isStrictlyNumeric(id)) {
                return false;
            }
        }
        return true;
    }
}
