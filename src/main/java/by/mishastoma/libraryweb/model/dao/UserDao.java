package by.mishastoma.libraryweb.model.dao;


import by.mishastoma.libraryweb.model.entity.User;
import by.mishastoma.libraryweb.exception.DaoException;

import java.util.Optional;

public interface UserDao extends BaseDao<User>{
    Optional<User> signIn(String login, String password) throws DaoException;
    boolean loginExists(String login) throws DaoException;
    boolean emailExists(String email) throws DaoException;
    long getIdByLogin(String login) throws DaoException;
    Optional<User> getUserById(long id) throws DaoException;
}
