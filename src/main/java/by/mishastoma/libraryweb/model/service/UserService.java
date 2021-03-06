package by.mishastoma.libraryweb.model.service;

import by.mishastoma.libraryweb.model.entity.User;
import by.mishastoma.libraryweb.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    Optional<User> signIn(String signInLogin, String password) throws ServiceException;

    Optional<User> signUp(Map<String, String> userMap, Set<String> invalids) throws ServiceException;

    Optional<User> getUserById(long id) throws ServiceException;

    List<User> getAll() throws ServiceException;

    boolean addToUsersBalance(long id, String amount) throws ServiceException;

    boolean subtractFromUsersBalance(long id, int amount) throws ServiceException;

    int getUserBalance(long id) throws ServiceException;

    boolean setIsBlockState(long id, boolean isBlocked) throws ServiceException;

    boolean changeUsersPassword(long id, String oldPassword, String newPassword) throws ServiceException;

    boolean changeUsersRole(long id, String role) throws ServiceException;
}
