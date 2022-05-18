package by.mishastoma.libraryweb.controller.pool.service;

import by.mishastoma.libraryweb.exception.ServiceException;

public interface UserService {
    boolean authenticate(String signInLogin, String password) throws ServiceException;
}
