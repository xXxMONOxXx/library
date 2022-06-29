package by.mishastoma.libraryweb.model.service;

import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> findAll() throws ServiceException;
    Optional<Genre> insert(String name) throws ServiceException;
}
