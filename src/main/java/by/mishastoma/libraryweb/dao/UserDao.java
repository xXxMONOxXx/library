package by.mishastoma.libraryweb.dao;

import by.mishastoma.libraryweb.exception.DaoException;

public interface UserDao {
    boolean authenticate (String signInLogin, String password) throws DaoException;
    //todo
}
