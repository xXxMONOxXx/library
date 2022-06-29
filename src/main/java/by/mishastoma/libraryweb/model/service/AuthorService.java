package by.mishastoma.libraryweb.model.service;

import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.entity.Author;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface AuthorService {
    Optional<Author> insert(Map<String, String> authorMap, Set<String> invalids) throws ServiceException;
    List<Author> findAll() throws ServiceException;
}
