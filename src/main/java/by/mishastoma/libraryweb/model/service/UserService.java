package by.mishastoma.libraryweb.model.service;

import by.mishastoma.libraryweb.model.entity.User;
import by.mishastoma.libraryweb.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    Optional<User> signIn (String signInLogin, String password) throws ServiceException;
    Optional<User> signUp(Map<String, String> userMap, Set<String> invalids) throws ServiceException;
    Optional<User> getUserById(long id) throws ServiceException;
    List<User> findAll() throws ServiceException;
}
