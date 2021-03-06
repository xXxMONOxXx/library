package by.mishastoma.libraryweb.validator.impl;

import by.mishastoma.libraryweb.validator.UserValidator;
import com.mysql.cj.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class UserValidatorImpl implements UserValidator {

    private static final String LOGIN_REGEX = "^[A-Za-zА-Яа-я]{3,20}$";
    private static final String NAME_REGEX = "^[A-Za-zА-Яа-я]{2,25}$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&-+=()])(?=\\S+$).{8,20}$";
    private static final String EMAIL_REGEX = "^([a-z0-9-]+.)*[a-z0-9-]+@[a-z0-9-]+(.[a-z0-9-]+)*.[a-z]{2,6}$";
    private static final String BIRTHDATE_REGEX = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
    private static final String NUMBER_REGEX = "^[0-9]{1,4}$";

    private UserValidatorImpl() {

    }

    public static UserValidatorImpl instance;


    public static UserValidatorImpl getInstance() {
        if (instance == null) {
            instance = new UserValidatorImpl();
        }
        return instance;
    }

    @Override
    public boolean isValidLogin(String login) {
        if (StringUtils.isEmptyOrWhitespaceOnly(login)) {
            return false;
        }
        Pattern pattern = Pattern.compile(LOGIN_REGEX);
        return pattern.matcher(login).matches();
    }

    @Override
    public boolean isValidFirstName(String firstName) {
        if (StringUtils.isEmptyOrWhitespaceOnly(firstName)) {
            return false;
        }
        Pattern pattern = Pattern.compile(NAME_REGEX);
        return pattern.matcher(firstName).matches();
    }

    @Override
    public boolean isValidLastName(String lastName) {
        if (StringUtils.isEmptyOrWhitespaceOnly(lastName)) {
            return false;
        }
        Pattern pattern = Pattern.compile(NAME_REGEX);
        return pattern.matcher(lastName).matches();
    }

    @Override
    public boolean isValidPassword(String password) {
        if (StringUtils.isEmptyOrWhitespaceOnly(password)) {
            return false;
        }
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        return pattern.matcher(password).matches();
    }

    @Override
    public boolean isValidEmail(String email) {
        if (StringUtils.isEmptyOrWhitespaceOnly(email)) {
            return false;
        }
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }

    @Override
    public boolean isValidBirthDate(String birthdate) {
        if (StringUtils.isEmptyOrWhitespaceOnly(birthdate)) {
            return false;
        }
        Pattern pattern = Pattern.compile(BIRTHDATE_REGEX);
        if (!pattern.matcher(birthdate).matches()) {
            return false;
        }
        try {
            LocalDate date = LocalDate.parse(birthdate);
            return !date.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public boolean isValidBalance(String balance) {
        if (StringUtils.isEmptyOrWhitespaceOnly(balance)) {
            return false;
        }
        Pattern pattern = Pattern.compile(NUMBER_REGEX);
        return pattern.matcher(balance).matches();
    }
}
