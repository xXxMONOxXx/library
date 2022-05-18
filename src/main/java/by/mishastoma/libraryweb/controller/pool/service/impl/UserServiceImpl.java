package by.mishastoma.libraryweb.controller.pool.service.impl;

import by.mishastoma.libraryweb.controller.pool.service.UserService;
import by.mishastoma.libraryweb.dao.impl.UserDaoImpl;
import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.exception.ServiceException;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl(){

    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String signInLogin, String password) throws ServiceException{
        boolean match;
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try{
            match = userDao.authenticate(signInLogin,password);
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        //todo validate login, password + sha256
        return match;
    }
}
