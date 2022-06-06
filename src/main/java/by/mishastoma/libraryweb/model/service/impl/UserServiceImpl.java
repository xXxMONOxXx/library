package by.mishastoma.libraryweb.model.service.impl;

import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.model.dao.UserDao;
import by.mishastoma.libraryweb.model.dao.impl.UserDaoImpl;
import by.mishastoma.libraryweb.model.entity.User;
import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.service.UserService;
import by.mishastoma.libraryweb.validator.UserValidator;
import by.mishastoma.libraryweb.validator.impl.UserValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;


public class UserServiceImpl implements UserService {


    private static UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl() {

    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<User> signIn(String login, String password) throws ServiceException {
        Optional<User> optionalUser = Optional.empty();
        UserValidator validator = UserValidatorImpl.getInstance();

        if (true) { // todo validate (validator.isValidLogin(login) && validator.isValidPassword(password))
            try { // todo add new sign in methods (email)
                UserDao dao = UserDaoImpl.getInstance();
                optionalUser = dao.signIn(login, password);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return optionalUser;
    }

    @Override
    public Optional<User> signUp(HttpServletRequest request, Set<String> invalids) throws ServiceException {
        Optional<User> optionalUser = Optional.empty();

        UserValidator validator = UserValidatorImpl.getInstance();
        String login = request.getParameter(ParameterName.LOGIN);
        String firstName = request.getParameter(ParameterName.FIRST_NAME);
        String lastName = request.getParameter(ParameterName.LAST_NAME);
        String email = request.getParameter(ParameterName.EMAIL);
        String password = request.getParameter(ParameterName.PASSWORD);
        String passwordConfirm = request.getParameter(ParameterName.PASSWORD_REPEAT);
        String birthdate = request.getParameter(ParameterName.BIRTHDATE);
        boolean isValid = true;
        if (!validator.isValidLogin(login)) {
            isValid = false;
            invalids.add(ParameterName.SIGN_UP_LOGIN_IS_INVALID);
            //todo add boolean isValidLogin for jsp and show message in different lang
        }
        if (!validator.isValidFirstName(firstName)) {
            invalids.add(ParameterName.SIGN_UP_FIRSTNAME_IS_INVALID);
            isValid = false;
        }
        if (!validator.isValidLastName(lastName)) {
            invalids.add(ParameterName.SIGN_UP_LASTNAME_IS_INVALID);
            isValid = false;
        }
        if (!validator.isValidEmail(email)) {
            invalids.add(ParameterName.SIGN_UP_EMAIL_IS_INVALID);
            isValid = false;
        }
        if (!validator.isValidPassword(password)) {
            invalids.add(ParameterName.SIGN_UP_PASSWORD_IS_INVALID);
            isValid = false;
        } else {
            if (!password.equals(passwordConfirm)) {
                invalids.add(ParameterName.SIGN_UP_PASSWORD_CONFIRM_IS_INVALID);
                isValid = false;
            }
        }
        if (!validator.isValidBirthDate(birthdate)) {
            invalids.add(ParameterName.SIGN_UP_BIRTHDATE_IS_INVALID);
            isValid = false;
        }

        if (isValid) {
            String passwordEncryptor; //todo
            User user = new User.Builder(-1). //todo is it normal? I think not ;(
                    withLogin(login).
                    withFirstName(firstName).
                    withLastName(lastName).
                    withEmail(email).
                    withPassword(password).
                    withBirthdate(LocalDate.parse(birthdate)).
                    build();
            try {
                UserDao userDao = UserDaoImpl.getInstance();
                if (userDao.emailExists(email)) {
                    invalids.add(ParameterName.SIGN_UP_EMAIL_IS_INVALID);
                }
                else{
                    if(userDao.loginExists(login)){
                        invalids.add(ParameterName.SIGN_UP_LOGIN_IS_INVALID);
                    }
                    else{
                        optionalUser = Optional.of(user);
                        userDao.insert(user);
                    }
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return optionalUser;
    }
}
