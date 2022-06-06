package by.mishastoma.libraryweb.model.service;

import by.mishastoma.libraryweb.model.entity.User;
import by.mishastoma.libraryweb.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;
import java.util.Set;

public interface UserService {
    Optional<User> signIn (String signInLogin, String password) throws ServiceException;
    Optional<User> signUp(HttpServletRequest request, Set<String> invalids) throws ServiceException;
}
