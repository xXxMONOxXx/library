package by.mishastoma.libraryweb.model.service;

import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> getAll() throws ServiceException;
    Optional<Genre> addGenre(String name) throws ServiceException;
    Optional<Genre> getById(long id) throws ServiceException;
    boolean updateGenre (long id, String name) throws ServiceException;
    boolean deleteGenre(long id) throws ServiceException;
}
