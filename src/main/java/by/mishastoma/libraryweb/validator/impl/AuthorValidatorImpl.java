package by.mishastoma.libraryweb.validator.impl;

import by.mishastoma.libraryweb.validator.AuthorValidator;
import com.mysql.cj.util.StringUtils;

import java.util.regex.Pattern;

public class AuthorValidatorImpl implements AuthorValidator {

    public static AuthorValidatorImpl instance;

    private AuthorValidatorImpl(){

    }

    public static AuthorValidatorImpl getInstance(){
        if(instance==null){
            instance = new AuthorValidatorImpl();
        }
        return instance;
    }


    public static final String NAME_REGEX ="^[A-Za-zА-Яа-я]{2,25}$";
    public static final int BIOGRAPHY_MAX_LENGTH = 1000;

    @Override
    public boolean isValidFirstName(String firstname) {
        if(StringUtils.isEmptyOrWhitespaceOnly(firstname)){
            return false;
        }
        Pattern pattern = Pattern.compile(NAME_REGEX);
        return pattern.matcher(firstname).matches();
    }

    @Override
    public boolean isValidLastName(String lastname) {
        if(StringUtils.isEmptyOrWhitespaceOnly(lastname)){
            return false;
        }
        Pattern pattern = Pattern.compile(NAME_REGEX);
        return pattern.matcher(lastname).matches();
    }

    @Override
    public boolean isValidBiography(String biography) {
        if(!StringUtils.isEmptyOrWhitespaceOnly(biography)){
            return biography.length() < BIOGRAPHY_MAX_LENGTH;
        }
        return true;
    }
}
