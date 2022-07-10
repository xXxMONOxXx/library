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
    int getUsersBalance (long id) throws DaoException;
    boolean updateUsersBalance (long id, int balance) throws DaoException;
    boolean changeUserState(long id, boolean isBlocked) throws DaoException;
    boolean changeUsersPassword(long id, String password) throws DaoException;
    String getUsersPassword(long id) throws DaoException;
    boolean changeUsersRole(long id, String role) throws DaoException;
}
