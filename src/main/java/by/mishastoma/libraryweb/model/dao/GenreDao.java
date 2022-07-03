package by.mishastoma.libraryweb.model.dao;

import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.entity.Author;
import by.mishastoma.libraryweb.model.entity.Genre;

import java.util.Optional;

public interface GenreDao extends BaseDao<Genre> {
    long getIdByName(String name) throws DaoException;
    Optional<Genre> getGenreById(long id) throws DaoException;
}
