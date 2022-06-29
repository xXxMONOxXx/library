package by.mishastoma.libraryweb.model.dao;

import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.entity.Genre;

public interface GenreDao extends BaseDao<Genre> {
    long getIdByName(String name) throws DaoException;
}
