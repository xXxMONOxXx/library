package by.mishastoma.libraryweb.service;

import by.mishastoma.libraryweb.exception.ServiceException;

public interface UserService {
    boolean loginExists(String login) throws ServiceException;
    boolean emailExists(String email) throws ServiceException;
}
