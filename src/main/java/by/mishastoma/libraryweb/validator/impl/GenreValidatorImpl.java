package by.mishastoma.libraryweb.validator.impl;

import by.mishastoma.libraryweb.validator.GenreValidator;
import com.mysql.cj.util.StringUtils;

import java.util.regex.Pattern;

public class GenreValidatorImpl implements GenreValidator {

    private static final String GENRE_NAME_REGEX = "^[A-Za-zА-Яа-я]{3,20}$";

    public static GenreValidatorImpl instance;

    private GenreValidatorImpl(){

    }

    public static GenreValidatorImpl getInstance(){
        if(instance==null){
            instance = new GenreValidatorImpl();
        }
        return instance;
    }

    @Override
    public boolean isValidName(String name) {
        if(StringUtils.isEmptyOrWhitespaceOnly(name)){
            return false;
        }
        Pattern pattern = Pattern.compile(GENRE_NAME_REGEX);
        return pattern.matcher(name).matches();
    }

}
