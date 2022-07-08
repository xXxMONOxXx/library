package by.mishastoma.libraryweb.model.service.impl;

import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.model.dao.UserDao;
import by.mishastoma.libraryweb.model.dao.impl.UserDaoImpl;
import by.mishastoma.libraryweb.model.entity.User;
import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.entity.UserRole;
import by.mishastoma.libraryweb.model.service.UserService;
import by.mishastoma.libraryweb.validator.UserValidator;
import by.mishastoma.libraryweb.validator.impl.UserValidatorImpl;

import java.time.LocalDate;
import java.util.*;


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

        if (validator.isValidLogin(login) && validator.isValidPassword(password)) {
            try { // todo add new sign in methods (email) AND check for is_blocked
                UserDao dao = UserDaoImpl.getInstance();
                optionalUser = dao.signIn(login, password);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return optionalUser;
    }

    @Override
    public Optional<User> signUp(Map<String, String> mapUser, Set<String> invalids) throws ServiceException {
        Optional<User> optionalUser = Optional.empty();
        if (isValidUser(mapUser, invalids)) {
            String passwordEncryptor; //todo
            User user = new User.Builder(-1). //todo is it normal? I think not ;(
                    withLogin(mapUser.get(ParameterName.LOGIN)).
                    withFirstName(mapUser.get(ParameterName.FIRST_NAME)).
                    withLastName(mapUser.get(ParameterName.LAST_NAME)).
                    withEmail(mapUser.get(ParameterName.EMAIL)).
                    withPassword(mapUser.get(ParameterName.PASSWORD)).
                    withBirthdate(LocalDate.parse(mapUser.get(ParameterName.BIRTHDATE))).
                    withRole(UserRole.USER).
                    build();
            try {
                UserDao userDao = UserDaoImpl.getInstance();
                if (userDao.emailExists(mapUser.get(ParameterName.EMAIL))) {
                    invalids.add(ParameterName.SIGN_UP_EMAIL_IS_INVALID);
                }
                else{
                    if(userDao.loginExists(mapUser.get(ParameterName.LOGIN))){
                        invalids.add(ParameterName.SIGN_UP_LOGIN_IS_INVALID);
                    }
                    else{
                        userDao.insert(user);
                        user.setId(userDao.getIdByLogin(user.getLogin()));
                        optionalUser = Optional.of(user);
                    }
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return optionalUser;
    }

    @Override
    public Optional<User> getUserById(long id) throws ServiceException {
        Optional<User> optionalUser;
        try {
            UserDao userDao = UserDaoImpl.getInstance();
            optionalUser = userDao.getUserById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return optionalUser;
    }

    @Override
    public List<User> getAll() throws ServiceException {
        List<User> users;
        try {
            UserDao userDao = UserDaoImpl.getInstance();
            users = userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public boolean addToUsersBalance(long id, String amount) throws ServiceException {
        UserValidator validator = UserValidatorImpl.getInstance();
        if(!validator.isValidBalance(amount)){
            return false;
        }
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            int currentBalance = userDao.getUsersBalance(id);
            return userDao.updateUsersBalance(id, currentBalance + Integer.parseInt(amount));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean subtractFromUsersBalance(long id, int amount) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            int currentBalance = userDao.getUsersBalance(id);
            if(currentBalance < amount){
                return false;
            }
            return userDao.updateUsersBalance(id, currentBalance - amount);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getUserBalance(long id) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            return userDao.getUsersBalance(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean setIsBlockState(long id, boolean isBlocked) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            return userDao.changeUserState(id, isBlocked);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changeUsersPassword(long id, String oldPassword, String newPassword) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        try{
            UserValidator validator = UserValidatorImpl.getInstance();
            if(!validator.isValidPassword(newPassword)){
                return false;
            }
            String passwordFromDb = userDao.getUsersPassword(id);
            if(passwordFromDb == null){
                return false;
            }
            if(!passwordFromDb.equals(oldPassword)){
                return false;
            }
            return userDao.changeUsersPassword(id, newPassword);

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private boolean isValidUser(Map<String, String> mapUser, Set<String> invalids){
        UserValidator validator = UserValidatorImpl.getInstance();
        if (!validator.isValidLogin(mapUser.get(ParameterName.LOGIN))) {
            invalids.add(ParameterName.SIGN_UP_LOGIN_IS_INVALID);
        }
        if (!validator.isValidFirstName(mapUser.get(ParameterName.FIRST_NAME))) {
            invalids.add(ParameterName.SIGN_UP_FIRSTNAME_IS_INVALID);
        }
        if (!validator.isValidLastName(mapUser.get(ParameterName.LAST_NAME))) {
            invalids.add(ParameterName.SIGN_UP_LASTNAME_IS_INVALID);
        }
        if (!validator.isValidEmail(mapUser.get(ParameterName.EMAIL))) {
            invalids.add(ParameterName.SIGN_UP_EMAIL_IS_INVALID);
        }
        if (!validator.isValidPassword(mapUser.get(ParameterName.PASSWORD))) {
            invalids.add(ParameterName.SIGN_UP_PASSWORD_IS_INVALID);
        } else {
            if (!mapUser.get(ParameterName.PASSWORD).equals(mapUser.get(ParameterName.PASSWORD_REPEAT))) {
                invalids.add(ParameterName.SIGN_UP_PASSWORD_CONFIRM_IS_INVALID);
            }
        }
        if (!validator.isValidBirthDate(mapUser.get(ParameterName.BIRTHDATE))) {
            invalids.add(ParameterName.SIGN_UP_BIRTHDATE_IS_INVALID);
        }
        return invalids.isEmpty();
    }
}
